package com.ms.sittingclient.stats_window

import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import org.springframework.stereotype.Component

@Component
class PieChartPercentageCalculator {
    fun calculate(data: ObservableList<PieChart.Data>): ObservableList<PieChart.Data> {
        data.forEach { it.name = it.name + " " + it.pieValue * 100 + "%" }
        return data
    }
}