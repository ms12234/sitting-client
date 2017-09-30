package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.PieChart
import java.time.LocalDateTime

class BreakToSittingDataDownload(start: LocalDateTime, end: LocalDateTime, measurementRepository: MeasurementRepository)
    : Task<ObservableList<PieChart.Data>>() {

    override fun call(): ObservableList<PieChart.Data> {
        return FXCollections.observableArrayList()
    }
}