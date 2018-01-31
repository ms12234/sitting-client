package com.ms.sittingclient.repository

import feign.Headers
import feign.Param
import feign.RequestLine
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Headers("Accept: application/json", "Content-Type: application/json")
interface MeasurementRepository {
    @RequestLine("GET /measurement?begin={begin}&end={end}")
    fun getMeasurements(@Param(value = "begin")
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                        begin: LocalDateTime,

                        @Param(value = "end")
                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                        end: LocalDateTime): List<Measurement>
}
