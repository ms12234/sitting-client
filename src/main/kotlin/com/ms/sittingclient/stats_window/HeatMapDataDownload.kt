package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.concurrent.Task
import javafx.scene.paint.Paint
import java.time.LocalDateTime

class HeatMapDataDownload(start: LocalDateTime, end: LocalDateTime,
                          measurementRepository: MeasurementRepository, insertFakeData: Boolean) :
        Task<List<Paint>>() {

    override fun call(): List<Paint> {
        return emptyList()
    }
}