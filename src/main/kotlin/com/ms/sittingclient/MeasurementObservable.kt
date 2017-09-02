package com.ms.sittingclient

import com.ms.sittingclient.repository.Measurement

interface MeasurementObservable {
    fun onNext(measurement: Measurement)

    fun onError(throwable: Throwable)
}
