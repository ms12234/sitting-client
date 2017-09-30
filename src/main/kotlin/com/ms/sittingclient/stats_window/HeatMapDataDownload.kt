package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.concurrent.Task
import java.time.LocalDateTime

class HeatMapDataDownload(dateTimeValue: LocalDateTime, dateTimeValue1: LocalDateTime, measurementRepository: MeasurementRepository) : Task<List<Double>>() {

    override fun call(): List<Double> {
        return emptyList()
    }
}