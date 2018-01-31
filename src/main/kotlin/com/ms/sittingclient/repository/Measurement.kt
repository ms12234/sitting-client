package com.ms.sittingclient.repository


import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class Measurement(var id: String,
                       @JsonFormat(pattern =
                       "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "GMT+1") var time:
                       Date,
                       var sensors: List<Sensor>,
                       var grade: Float) {

    constructor() : this("", Date(), emptyList(), 0F)
}
