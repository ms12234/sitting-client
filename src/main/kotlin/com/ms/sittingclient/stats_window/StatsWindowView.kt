package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import javafx.collections.FXCollections
import javafx.scene.chart.BarChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.PieChart
import javafx.scene.control.ComboBox
import javafx.scene.control.SingleSelectionModel
import javafx.scene.layout.HBox
import tornadofx.*
import tornadofx.control.DateTimePicker

class StatsWindowView : View("Statystki") {
    private val controller: StatsWindowController by di()

    private var graphsComboBox: ComboBox<String> by singleAssign()
    private var periodStartPicker: DateTimePicker by singleAssign()
    private var periodEndPicker: DateTimePicker by singleAssign()

    private var dailySittingGraph: BarChart<String, Number> by singleAssign()
    private var breakToSittingGraph: PieChart by singleAssign()
    private var okToNotOkSittingGraph: PieChart by singleAssign()
    private var heatMapGraphPanel: HBox by singleAssign()

    override val root = vbox {
        hbox {
            label("Wykres")
            graphsComboBox = combobox()
            graphsComboBox.items = controller.graphs.get.keys.toList().observable()
            graphsComboBox.selectionModelProperty().addListener({ _, _, newValue ->
                changeGraphsVisibility(newValue)
            })

            label("Początek zakresu")
            periodStartPicker = DateTimePicker()
            add(periodStartPicker)

            label("Koniec zakresu")
            periodEndPicker = DateTimePicker()
            add(periodEndPicker)

            button("Pokaż") {
                action {
                    when (graphsComboBox.selectionModel.selectedIndex) {
                        0 -> controller.showGraphDailySitting(dailySittingGraph,
                                periodStartPicker.dateTimeValue,
                                periodEndPicker.dateTimeValue)

                        1 -> {

                        }
                        2 -> {

                        }
                        3 -> {

                        }
                    }
                }
            }
        }

        hbox {
            progressindicator { progress = 10.0 }

            dailySittingGraph = barchart("", CategoryAxis(), NumberAxis())
            breakToSittingGraph = piechart("", FXCollections.observableArrayList())
            okToNotOkSittingGraph = piechart("", FXCollections.observableArrayList())
            heatMapGraphPanel = hbox {

            }

            style {
                minWidth = 1000.px
                minHeight = 900.px
            }
        }

        style {
            addClass(MyStyle.default)
        }
    }

    private fun changeGraphsVisibility(newValue: SingleSelectionModel<String>) {
        when (newValue.selectedIndex) {
            0 -> {
                dailySittingGraph.isVisible = true
                breakToSittingGraph.isVisible = false
                okToNotOkSittingGraph.isVisible = false
                heatMapGraphPanel.isVisible = false
            }
            1 -> {
                dailySittingGraph.isVisible = false
                breakToSittingGraph.isVisible = true
                okToNotOkSittingGraph.isVisible = false
                heatMapGraphPanel.isVisible = false
            }
            2 -> {
                dailySittingGraph.isVisible = false
                breakToSittingGraph.isVisible = false
                okToNotOkSittingGraph.isVisible = true
                heatMapGraphPanel.isVisible = false
            }
            3 -> {
                dailySittingGraph.isVisible = false
                breakToSittingGraph.isVisible = false
                okToNotOkSittingGraph.isVisible = false
                heatMapGraphPanel.isVisible = true
            }
        }
    }
}