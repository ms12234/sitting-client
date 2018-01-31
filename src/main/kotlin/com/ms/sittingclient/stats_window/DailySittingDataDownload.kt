package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.Repository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.XYChart
import java.time.LocalDate
import java.time.LocalDateTime

class DailySittingDataDownload(private val start: LocalDateTime,
                               private val end: LocalDateTime,
                               private val measurementRepository: Repository,
                               private val insertFakeData: Boolean) :
        Task<ObservableList<XYChart.Series<String, Number>>>() {

    override fun call(): ObservableList<XYChart.Series<String, Number>> {
        updateProgress(0, 100)

        if (insertFakeData) {
            return returnFakeData()
        } else {
            return returnRealData()
        }
    }

    private fun returnRealData(): ObservableList<XYChart.Series<String, Number>> {
        val measurements = measurementRepository.getMeasurements(start, end)
        updateProgress(80, 100)
        if (measurements.isEmpty()) {
            updateProgress(100, 100)
            return FXCollections.observableArrayList<XYChart.Series<String, Number>>()
        }

        val sittingTimesPerDay = calculateSittingTimeForEachDay(measurements)
        return createDataFromSittingTimes(sittingTimesPerDay)
    }

    private fun createDataFromSittingTimes(sittingTimesPerDay: List<Pair<LocalDate, Int>>):
            ObservableList<XYChart.Series<String, Number>> {
        val data = XYChart.Series<String, Number>()
        data.name = "Ilość siedzenia w ciągu dnia"

        sittingTimesPerDay.forEach {
            data.data.add(XYChart.Data(it.first.toString(), it.second.toDouble() / 3600.0))
        }

        return FXCollections.observableArrayList(data)
    }

    private fun calculateSittingTimeForEachDay(measurements: List<Measurement>):
            List<Pair<LocalDate, Int>> {

//        val uniqueDays = measurements
//                .map { it.time.toLocalDate() }
//                .sortedBy { it }
//                .distinct()
        val daysWithCalculatedSittingTimes = mutableListOf<Pair<LocalDate, Int>>()

//        uniqueDays.forEach { uniqueDay ->
//            val sittingSecondsInThisDay = measurements.count {
//                uniqueDay.isEqual(it.time.toLocalDate())
//            }
//            daysWithCalculatedSittingTimes.add(Pair(uniqueDay, sittingSecondsInThisDay))
//        }

        return daysWithCalculatedSittingTimes
    }

    private fun returnFakeData(): ObservableList<XYChart.Series<String, Number>> {
        val data = XYChart.Series<String, Number>()
        data.name = "Ilość siedzenia w ciągu dnia"
        data.data.add(XYChart.Data(LocalDate.now().minusDays(9).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(8).toString(),
                9.1))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(7).toString(),
                2.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(6).toString(),
                3))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(5).toString(),
                3.5))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(4).toString(),
                1.2))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(3).toString(),
                2.7))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(2).toString(),
                10.9))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(1).toString(),
                8.8))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(0).toString(),
                2.4))
        updateProgress(100, 100)
        return FXCollections.observableArrayList(data)
    }
}