package com.ms.sittingclient.settings_window

import javafx.scene.control.TextField
import tornadofx.*

class SettingsWindowView : View("Ustawienia przerw") {

    private val controller: SettingsWindowController by di()
    private var breakMinutesTextField: TextField by singleAssign()
    private var breakSecondsTextField: TextField by singleAssign()
    private var workMinutesTextField: TextField by singleAssign()
    private var workSecondsTextField: TextField by singleAssign()

    override val root = vbox {
        gridpane {
            row {
                label("Długość przerwy")
                breakMinutesTextField = textfield()
                label("min")
                breakSecondsTextField = textfield()
                label("sek")
            }

            row {
                label("Długość przerwy (0-99)")
                workMinutesTextField = textfield()
                label("min")
                workSecondsTextField = textfield()
                label("sek")
            }
        }

        hbox {
            button("Zapisz") {
                action {
                    breakMinutesTextField.commitValue()
                    breakSecondsTextField.commitValue()
                    workMinutesTextField.commitValue()
                    workSecondsTextField.commitValue()
                    close()
                }
            }

            button("Anuluj") {
                action {
                    breakMinutesTextField.cancelEdit()
                    breakSecondsTextField.cancelEdit()
                    workMinutesTextField.cancelEdit()
                    workSecondsTextField.cancelEdit()
                }
            }
        }
    }

    init {
        controller.loadFromFile()

        breakMinutesTextField.bind(controller.breakMinutes)
        breakSecondsTextField.bind(controller.breakSeconds)
        workMinutesTextField.bind(controller.workMinutes)
        workSecondsTextField.bind(controller.workSeconds)
    }
}