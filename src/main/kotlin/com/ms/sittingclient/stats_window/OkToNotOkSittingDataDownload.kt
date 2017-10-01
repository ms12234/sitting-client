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
                                   private val pieChartPercentageCalculator:
                                   PieChartPercentageCalculator,
                                   private val insertFakeData: Boolean)
    : Task<ObservableList<PieChart.Data>>() {

    override fun call(): ObservableList<PieChart.Data> {
        updateProgress(0, 100)

        if (insertFakeData) {
            return pieChartPercentageCalculator.calculate(returnFakeData())
        } else {
            return pieChartPercentageCalculator.calculate(returnRealData())
        }
    }

    private fun returnRealData(): ObservableList<PieChart.Data> {
        val measurements = measurementRepository.getMeasurements(start, end)
        progress(0)

        if (measurements.isEmpty()) {
            return returnEmptyData()
        }

        val okMeasurementsCount = measurements.count { it.grade == 1.0F }

        val data = createDataList(okMeasurementsCount, measurements.size)

        progress(100)
        return data
    }

    private fun createDataList(okMeasurementsCount: Int, totalMeasurements: Int):
            ObservableList<PieChart.Data> {

        val data = FXCollections.observableArrayList<PieChart.Data>()
        val okPercentage = okMeasurementsCount.toDouble() / totalMeasurements.toDouble()

        data.add(PieChart.Data("Poprawne siedzenie", okPercentage))
        data.add(PieChart.Data("Niepoprawne siedzenie", 1.0 - okPercentage))
        return data
    }

    private fun returnEmptyData(): ObservableList<PieChart.Data> {
        progress(100)
        return FXCollections.observableArrayList(PieChart.Data("Brak " +
                "danych", 1.0))
    }

    private fun returnFakeData(): ObservableList<PieChart.Data> {
        progress(100)
        return createDataList(87, 100)
    }

    private fun progress(value: Long) {
        updateProgress(value, 100)
    }
}