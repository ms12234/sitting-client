package com.ms.sittingclient.repository

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class Repository(val measurementRepository: MeasurementRepository) {
    fun getMeasurements(start: LocalDateTime, end: LocalDateTime): List<Measurement> {
        val begin = Date.from(start.atZone(ZoneId.systemDefault()).toInstant())
        val endi = Date.from(end.atZone(ZoneId.systemDefault()).toInstant())

        return measurementRepository.getMeasurements(start, end)
    }
}