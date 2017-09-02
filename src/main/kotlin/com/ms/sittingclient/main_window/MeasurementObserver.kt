package com.ms.sittingclient.main_window

import com.ms.sittingclient.repository.Measurement

interface MeasurementObserver {
    fun onNext(measurement: Measurement)

    fun onError(error: Error)
}
