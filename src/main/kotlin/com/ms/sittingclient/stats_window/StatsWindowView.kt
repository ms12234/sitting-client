package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import javafx.scene.control.ComboBox
import tornadofx.*
import tornadofx.control.DateTimePicker

class StatsWindowView : View("Statystki") {
    private val controller: StatsWindowController by di()
    private var graphsComboBox: ComboBox<String> by singleAssign()
    private var periodStartPicker: DateTimePicker by singleAssign()
    private var periodEndPicker: DateTimePicker by singleAssign()

    override val root = hbox {
        hbox {
            label("Wykres")
            graphsComboBox = combobox(controller.graph)
            graphsComboBox.items = controller.graphs

            label("Początek zakresu")
            periodStartPicker = DateTimePicker()
            periodStartPicker.dateTimeValueProperty().bind(controller.start)
            add(periodStartPicker)

            label("Koniec zakresu")
            periodEndPicker = DateTimePicker()
            periodEndPicker.dateTimeValueProperty().bind(controller.end)
            add(periodEndPicker)
        }

        hbox {
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