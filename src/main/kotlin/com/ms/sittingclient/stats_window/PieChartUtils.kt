package com.ms.sittingclient.stats_window

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.chart.PieChart
import org.springframework.stereotype.Component

@Component
class PieChartUtils {
    fun calculatePercentages(data: ObservableList<PieChart.Data>): ObservableList<PieChart.Data> {
        data.forEach { it.name = it.name + " " + it.pieValue * 100 + "%" }
        return data
    }

    fun createDataList(okMeasurementsCount: Int, totalMeasurements: Int, okMessage: String,
                       notOkMessage: String):
            ObservableList<PieChart.Data> {

        val data = FXCollections.observableArrayList<PieChart.Data>()
        val okPercentage = okMeasurementsCount.toDouble() / totalMeasurements.toDouble()

        data.add(PieChart.Data(okMessage, okPercentage))
        data.add(PieChart.Data(notOkMessage, 1.0 - okPercentage))
        return data
    }
}