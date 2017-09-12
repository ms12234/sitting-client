package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.scene.layout.VBox
import tornadofx.*

class StatsWindowView : View("Statystki") {
    private val repository: MeasurementRepository by di()

    override val root = VBox()
}