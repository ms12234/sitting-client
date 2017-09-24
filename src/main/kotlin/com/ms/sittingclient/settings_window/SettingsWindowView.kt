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
                breakMinutesTextField = textfield(controller.breakMinutes)
                breakMinutesTextField.textProperty().addListener({ observable, oldValue, newValue ->
                    //TODO
                })
                label("min")

                breakSecondsTextField = textfield(controller.breakSeconds)
                breakSecondsTextField.textProperty().addListener({ observable, oldValue, newValue ->
                    //TODO
                })
                label("sek")
            }

            row {
                label("Długość przerwy (0-99)")
                workMinutesTextField = textfield(controller.workMinutes)
                workMinutesTextField.textProperty().addListener({ observable, oldValue, newValue ->
                    //TODO
                })
                label("min")

                workSecondsTextField = textfield(controller.workSeconds)
                workSecondsTextField.textProperty().addListener({ observable, oldValue, newValue ->
                    //TODO
                })
                label("sek")
            }
        }

        hbox {
            button("Anuluj") {
                action {
                    close()
                }
            }

            button("Zapisz") {
                action {
                    runAsync {
                        controller.saveToFile()
                    } ui {
                        close()
                    }
                }
            }
        }
    }

    init {
        runAsync {
            controller.readSettings()
        } ui {
            controller.assignValues(it)
        }
    }
}