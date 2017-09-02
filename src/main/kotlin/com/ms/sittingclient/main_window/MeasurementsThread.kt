package com.ms.sittingclient.main_window

import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.MeasurementRepository
import javafx.application.Platform
import java.time.LocalDateTime
import java.util.logging.Logger

class MeasurementsThread(private val repository: MeasurementRepository,
                         private val observer: MeasurementObserver,
                         private val lastSeconds: Long = 5,
                         private var millisecondsBetweenRefreshing: Int = 900) : Runnable {

    private val logger = Logger.getLogger(javaClass.simpleName)
    private var startTime = 0L

    override fun run() {
        while (true) {
            startTime = System.currentTimeMillis()
            val measurementsFromLastSeconds = mutableListOf<Measurement>()

            try {
                measurementsFromLastSeconds.addAll(repository.getMeasurements(
                        LocalDateTime.now().minusSeconds(lastSeconds),
                        LocalDateTime.now()))
            } catch (e: Exception) {
                logger.warning("Could not connect to server")
                informAboutError(Error.COULD_NOT_CONNECT)
                continue
            }

            if (measurementsFromLastSeconds.size == 0) {
                logger.warning("No measurements in last seconds")
                informAboutError(Error.NO_MEASUREMENTS_IN_LAST_SECONDS)
                continue
            }

            logger.info("Returning value")
            Platform.runLater {
                observer.onNext(measurementsFromLastSeconds[0])
            }

            waitForNextRefresh()
        }
    }

    private fun informAboutError(error: Error) {
        Platform.runLater { observer.onError(error) }

        waitForNextRefresh()
    }

    private fun waitForNextRefresh() {
        val elapsedTime = System.currentTimeMillis() - startTime
        Thread.sleep(millisecondsBetweenRefreshing - elapsedTime)
    }
}
