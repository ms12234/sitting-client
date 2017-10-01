package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.PieChart
import java.time.LocalDateTime

class BreakToSittingDataDownload(private val start: LocalDateTime,
                                 private val end: LocalDateTime,
                                 private val measurementRepository: MeasurementRepository,
                                 private val pieChartUtils: PieChartUtils,
                                 private val insertFakeData: Boolean)
    : Task<ObservableList<PieChart.Data>>() {

    override fun call(): ObservableList<PieChart.Data> {
        progress(0)

        if (insertFakeData) {
            return pieChartUtils.calculatePercentages(returnFakeData())
        } else {
            return pieChartUtils.calculatePercentages(returnRealData())
        }
    }

    private fun returnRealData(): ObservableList<PieChart.Data> {
        val measurements = measurementRepository.getMeasurements(start, end)
        if (measurements.isEmpty()) {
            //TODO return
        }

        val breakMeasurementsCount = measurements.count {
            it.sensors.filter { it.value == 0.0F }.forEach { false }
            true
        }

        return FXCollections.observableArrayList()
    }

    private fun returnFakeData(): ObservableList<PieChart.Data> {
        progress(100)
        return pieChartUtils.createDataList(614, 1000,
                "Czas siedzenia", "Czas przerw")
    }

    private fun progress(value: Long) {
        updateProgress(value, 100)
    }
}