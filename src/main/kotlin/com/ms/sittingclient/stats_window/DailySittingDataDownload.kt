package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.XYChart
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DailySittingDataDownload(private val start: LocalDateTime,
                               private val end: LocalDateTime,
                               private val measurementRepository: MeasurementRepository,
                               private val insertFakeData: Boolean) :
        Task<ObservableList<XYChart.Series<String, Number>>>() {

    private val maxDaysForGraph = 10

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
            //TODO
        }

        val sittingTimesPerDay = calculateSittingTimeForEachDay(measurements)
        if (sittingTimesPerDay.size < maxDaysForGraph) {
            //simple, no scaling
        } else {
            //scaling required
        }

        return FXCollections.observableArrayList()
    }

    private fun calculateSittingTimeForEachDay(measurements: List<Measurement>):
            List<Pair<LocalDate, Int>> {

        val uniqueDays = measurements.map { it.time.toLocalDate() }.distinct()
        val daysWithCalculatedSittingTimes = mutableListOf<Pair<LocalDate, Int>>()

        uniqueDays.forEach { uniqueDay ->
            val sittingSecondsInThisDay = measurements.count {
                uniqueDay.isEqual(it.time.toLocalDate())
            }
            daysWithCalculatedSittingTimes.add(Pair(uniqueDay, sittingSecondsInThisDay))
        }

        return daysWithCalculatedSittingTimes
    }

    private fun daysBetweenEarliestAndLatest(measurements: List<Measurement>): Int {
        val earliestDate = measurements.minBy { it.time }!!.time.toLocalDate()
        val latestDate = measurements.maxBy { it.time }!!.time.toLocalDate()
        return ChronoUnit.DAYS.between(earliestDate, latestDate).toInt()
    }

    private fun returnFakeData(): ObservableList<XYChart.Series<String, Number>> {
        val data = XYChart.Series<String, Number>()
        data.data.add(XYChart.Data(LocalDate.now().minusDays(0).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(1).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(2).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(3).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(4).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(5).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(6).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(7).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(8).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(9).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(10).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(11).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(12).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(13).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(14).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(15).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(16).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(17).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(18).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(19).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(20).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(21).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(22).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(23).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(24).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(25).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(26).toString(),
                8.4))
        data.data.add(XYChart.Data(LocalDate.now().minusDays(27).toString(),
                8.4))
        return FXCollections.observableArrayList(data)
    }
}