package com.ms.sittingclient

import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.MeasurementRepository
import javafx.application.Platform
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import java.util.logging.Logger

class MeasurementsThread(private val repository: MeasurementRepository,
                         private val measurementObservable: MeasurementObservable,
                         private var millisecondsBetweenRefreshing: Int = 900) : Runnable {

    private val logger = Logger.getLogger(javaClass.simpleName)

    override fun run() {
        while (true) {
            val startTime = System.currentTimeMillis()
            val measurementsFromLastSecond = mutableListOf<Measurement>()

            try {
                measurementsFromLastSecond.add(repository.getMeasurements(
                        LocalDateTime.now().minusSeconds(1),
                        LocalDateTime.now()))
            } catch (e: Exception) {

            }

            val gettingMeasurementAndAssessment = CompletableFuture
                    .supplyAsync<Any>(measurement, executorService)
                    .thenApplyAsync(Function<Any, U> { this.assesPosition(it) })
                    .exceptionally({ throwable ->
                        logger.warning("Exception while getting " + "measurement")
                        Platform.runLater { measurementObservable!!.onError(throwable) }
                        null
                    })

            try {
                val measurementAndAssessment = gettingMeasurementAndAssessment.get(millisecondsBetweenRefreshing.toLong(),
                        TimeUnit.MILLISECONDS)

                if (measurementAndAssessment == null) {
                    val elapsedTime = System.currentTimeMillis() - startTime
                    Thread.sleep(millisecondsBetweenRefreshing - elapsedTime)
                    continue
                }

                Platform.runLater {
                    measurementObservable!!.onNext(measurementAndAssessment!!.getValue0(),
                            measurementAndAssessment!!
                                    .getValue1())
                }

                val elapsedTime = System.currentTimeMillis() - startTime
                Thread.sleep(millisecondsBetweenRefreshing - elapsedTime)

            } catch (e: InterruptedException) {
                logger.warning("Timeout exception while waiting for " + "measurement")

                Platform.runLater { measurementObservable!!.onError(e) }
            } catch (e: ExecutionException) {
                logger.warning("Timeout exception while waiting for " + "measurement")
                Platform.runLater { measurementObservable!!.onError(e) }
            } catch (e: TimeoutException) {
                logger.warning("Timeout exception while waiting for " + "measurement")
                Platform.runLater { measurementObservable!!.onError(e) }
            }

        }
    }
}
