package com.ms.sittingclient.stats_window

import javafx.scene.chart.BarChart
import org.springframework.stereotype.Component
import tornadofx.*
import java.time.LocalDateTime

@Component
class StatsWindowController(val graphs: Graphs) : Controller() {

    fun showGraphDailySitting(dateTimeValue: BarChart<String, Number>,
                              dateTimeValue1: LocalDateTime, dateTimeValue2: LocalDateTime) {


    }
}