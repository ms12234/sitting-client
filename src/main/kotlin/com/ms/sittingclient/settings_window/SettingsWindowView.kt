package com.ms.sittingclient.settings_window

import com.ms.sittingclient.SettingsChangedEvent
import javafx.scene.control.Alert
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
                breakMinutesTextField.textProperty().addListener({ _, oldValue, newValue ->
                    checkTextField(breakMinutesTextField, newValue, oldValue)
                })
                label("min")

                breakSecondsTextField = textfield(controller.breakSeconds)
                breakSecondsTextField.textProperty().addListener({ _, oldValue, newValue ->
                    checkTextField(breakSecondsTextField, newValue, oldValue)
                })
                label("sek")
            }

            row {
                label("Długość przerwy")
                workMinutesTextField = textfield(controller.workMinutes)
                workMinutesTextField.textProperty().addListener({ _, oldValue, newValue ->
                    checkTextField(workMinutesTextField, newValue, oldValue)
                })
                label("min")

                workSecondsTextField = textfield(controller.workSeconds)
                workSecondsTextField.textProperty().addListener({ _, oldValue, newValue ->
                    checkTextField(workSecondsTextField, newValue, oldValue)
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
                        fire(SettingsChangedEvent())
                        close()
                    }
                }
            }
        }
    }

    private fun checkTextField(textField: TextField, newValue: String, oldValue: String) {
        try {
            val value = newValue.toInt()
            if (value < 1) {
                textField.text = oldValue
                showErrorDialog()
            }
        } catch (e: Exception) {
            textField.text = oldValue
            showErrorDialog()
        }
    }

    private fun showErrorDialog() {
        val dialog = Alert(Alert.AlertType.ERROR)
        dialog.title = "Niepoprawna wartość"
        dialog.headerText = "Wpisany znak był niepoprawny"
        dialog.contentText = "Dopuszczalne są jedynie cyfry"
        dialog.showAndWait()
    }

    init {
        runAsync {
            controller.readSettings()
        } ui {
            controller.assignValues(it)
        }
    }
}