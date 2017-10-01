package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.PieChart
import java.time.LocalDateTime

class OkToNotOkSittingDataDownload(private val start: LocalDateTime,
                                   private val end: LocalDateTime,
                                   private val measurementRepository: MeasurementRepository,
                                   private val pieChartUtils:
                                   PieChartUtils,
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

        val okMeasurementsCount = measurements.count { it.grade == 1.0F }

        val data = pieChartUtils.createDataList(okMeasurementsCount, measurements.size,
                "Poprawne siedzenie", "Niepoprawne siedzenie")

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
        return pieChartUtils.createDataList(87, 100,
                "Poprawne siedzenie", "Niepoprawne siedzenie")
    }

    private fun progress(value: Long) {
        updateProgress(value, 100)
    }
}