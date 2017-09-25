package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import javafx.scene.chart.BarChart
import javafx.scene.chart.PieChart
import javafx.scene.control.ComboBox
import tornadofx.*
import tornadofx.control.DateTimePicker
import java.time.LocalDate

class StatsWindowView : View("Statystki") {
    private val controller: StatsWindowController by di()

    private var graphsComboBox: ComboBox<String> by singleAssign()
    private var periodStartPicker: DateTimePicker by singleAssign()
    private var periodEndPicker: DateTimePicker by singleAssign()

    private var dailySittingGraph: BarChart<LocalDate, Double> by singleAssign()
    private var dayOfWeekSittingGraph: BarChart<LocalDate, Double> by singleAssign()
    private var breakToSittingGraph: PieChart by singleAssign()
    private var okToNotOkSittingGraph: PieChart by singleAssign()
//    private var heatMapGraph:

    override val root = hbox {
        hbox {
            label("Wykres")
            graphsComboBox = combobox()
            graphsComboBox.items = controller.graphs().observable()

            label("Początek zakresu")
            periodStartPicker = DateTimePicker()
            add(periodStartPicker)

            label("Koniec zakresu")
            periodEndPicker = DateTimePicker()
            add(periodEndPicker)

            button("Pokaż") {
                action {
                    when (graphsComboBox.selectionModel.selectedIndex) {

                    }
                }
            }
        }

        hbox {
            progressindicator { progress = 10.0 }


            style {
                minWidth = 1000.px
                minHeight = 1000.px
            }
        }

        style {
            addClass(MyStyle.default)
        }
    }
}