package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.scene.control.ComboBox
import tornadofx.*

class StatsWindowView : View("Statystki") {
    private val repository: MeasurementRepository by di()
    private var graphsComboBox: ComboBox<String> by singleAssign()

    override val root = hbox {
        hbox {
            label("Wykres")
            graphsComboBox = combobox {
                items = FXCollections.observableArrayList("asdf", "asdf", "asdf")
            }
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