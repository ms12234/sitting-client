package com.ms.sittingclient.repository

import java.time.LocalDateTime

data class Measurement(var time: LocalDateTime,
                       var sensors: List<Sensor>,
                       var grade: Float) {

    constructor() : this(LocalDateTime.now(), emptyList(), 0F)
}
