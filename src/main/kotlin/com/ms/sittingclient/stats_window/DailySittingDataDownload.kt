package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.XYChart
import java.time.LocalDateTime

class DailySittingDataDownload(start: LocalDateTime, end: LocalDateTime, measurementRepository: MeasurementRepository) :
        Task<ObservableList<XYChart.Series<String, Number>>>() {

    override fun call(): ObservableList<XYChart.Series<String, Number>> {
        updateProgress(10, 20)
        Thread.sleep(2000)
        return FXCollections.observableArrayList()
    }
}