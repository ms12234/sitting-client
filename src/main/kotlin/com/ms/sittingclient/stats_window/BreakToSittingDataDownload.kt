package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.Repository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.PieChart
import java.time.LocalDateTime

class BreakToSittingDataDownload(private val start: LocalDateTime,
                                 private val end: LocalDateTime,
                                 private val measurementRepository: Repository,
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
        progress(90)

        if (measurements.isEmpty()) {
            return returnEmptyData()
        }

        val breakMeasurementsCount = measurements.count {
            it.sensors.filter { it.value == 0.0 }.forEach { false }
            true
        }

        val data = pieChartUtils.createDataList(breakMeasurementsCount,
                measurements.size, "Czas przerw", "Czas siedzenia")
        progress(100)
        return data
    }

    private fun returnEmptyData(): ObservableList<PieChart.Data> {
        progress(100)
        return FXCollections.observableArrayList(PieChart.Data("Brak " +
                "danych", 1.0))
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