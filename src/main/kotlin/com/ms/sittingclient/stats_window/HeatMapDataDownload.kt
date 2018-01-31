package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.Repository
import javafx.concurrent.Task
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.*
import java.time.LocalDateTime

class HeatMapDataDownload(private val start: LocalDateTime,
                          private val end: LocalDateTime,
                          private val measurementRepository: Repository,
                          private val insertFakeData: Boolean) :
        Task<List<Paint>>() {

    override fun call(): List<Paint> {
        updateProgress(0, 100)

        if (insertFakeData) {
            return returnFakeData()
        } else {
            return returnRealData()
        }
    }

    private fun returnRealData(): List<Paint> {
        val measurements = measurementRepository.getMeasurements(start, end)
        if (measurements.isEmpty()) {
            updateProgress(100, 100)
            return returnAllBlackColors()
        }

        val okSittingAndTotalSitting = createListForCounting()
        fillCountingList(measurements, okSittingAndTotalSitting)

        updateProgress(100, 100)
        return okSittingAndTotalSitting.map { RectangleShade().assign(it.ok, it.total) }
    }

    private fun fillCountingList(measurements: List<Measurement>, okSittingAndTotalSitting: List<OkSitttingToTotalSitting>) {
        measurements.forEach { measurement ->
            for (i in 0..9) {
                okSittingAndTotalSitting[i].ok += measurement.sensors[i].value.toInt()
                okSittingAndTotalSitting[i].total += 1
            }
        }
    }

    private fun createListForCounting(): List<OkSitttingToTotalSitting> {
        val okSittingAndTotalSitting = mutableListOf<OkSitttingToTotalSitting>()
        for (i in 0..9) {
            okSittingAndTotalSitting.add(OkSitttingToTotalSitting(0, 0))
        }
        return okSittingAndTotalSitting
    }

    private fun returnAllBlackColors(): List<Color> {
        val colors = mutableListOf<Color>()

        for (i in 0..9) {
            colors.add(c("#000000"))
        }

        return colors
    }

    private fun returnFakeData(): List<Paint> {
        val values = mutableListOf<Paint>()
        val rectangleShader = RectangleShade()

        values.add(rectangleShader.assign(89, 100))
        values.add(rectangleShader.assign(93, 100))
        values.add(rectangleShader.assign(79, 100))
        values.add(rectangleShader.assign(98, 100))
        values.add(rectangleShader.assign(99, 100))
        values.add(rectangleShader.assign(96, 100))
        values.add(rectangleShader.assign(95, 100))
        values.add(rectangleShader.assign(94, 100))
        values.add(rectangleShader.assign(80, 100))

        updateProgress(100, 100)
        return values
    }
}