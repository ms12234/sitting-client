package com.ms.sittingclient.stats_window

import com.ms.sittingclient.repository.MeasurementRepository
import javafx.scene.chart.BarChart
import javafx.scene.chart.PieChart
import javafx.scene.shape.Rectangle
import org.springframework.stereotype.Component
import tornadofx.*
import java.time.LocalDateTime

@Component
class StatsWindowController(private val measurementRepository: MeasurementRepository)
    : Controller() {

    fun showGraphDailySitting(chart: BarChart<String, Number>, begin: LocalDateTime,
                              end: LocalDateTime) {
    }

    fun showGraphBreakToSitting(chart: PieChart, begin: LocalDateTime, end: LocalDateTime) {
    }

    fun showGraphOkToNotOkSitting(graph: PieChart, begin: LocalDateTime, end: LocalDateTime) {
    }

    fun showHeatMap(top0: Rectangle, top1: Rectangle, top2: Rectangle,
                    middle0: Rectangle, middle1: Rectangle, middle2: Rectangle,
                    bottom0: Rectangle, bottom1: Rectangle, bottom2: Rectangle) {
    }
}