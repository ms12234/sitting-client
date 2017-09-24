package com.ms.sittingclient.settings_window

import com.ms.sittingclient.MyStyle
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

                hbox {
                    breakMinutesTextField = textfield(controller.breakMinutes) {
                        addClass(MyStyle.settingInput)
                    }
                    breakMinutesTextField.textProperty().addListener({ _, oldValue, newValue ->
                        checkTextField(breakMinutesTextField, newValue,
                                oldValue, false)
                    })
                    label("min") {
                        addClass(MyStyle.settingsUnit)
                    }
                }

                hbox {
                    breakSecondsTextField = textfield(controller.breakSeconds) {
                        addClass(MyStyle.settingInput)
                    }
                    breakSecondsTextField.textProperty().addListener({ _, oldValue, newValue ->
                        checkTextField(breakSecondsTextField, newValue,
                                oldValue, true)
                    })
                    label("sek") {
                        addClass(MyStyle.settingsUnit)
                    }
                }
            }

            row {
                label("Długość pracy")

                hbox {
                    workMinutesTextField = textfield(controller.workMinutes) {
                        addClass(MyStyle.settingInput)
                    }
                    workMinutesTextField.textProperty().addListener({ _, oldValue, newValue ->
                        checkTextField(workMinutesTextField, newValue, oldValue, false)
                    })
                    label("min") {
                        addClass(MyStyle.settingsUnit)
                    }
                }

                hbox {
                    workSecondsTextField = textfield(controller.workSeconds) {
                        addClass(MyStyle.settingInput)
                    }
                    workSecondsTextField.textProperty().addListener({ _, oldValue, newValue ->
                        checkTextField(workSecondsTextField, newValue,
                                oldValue, true)
                    })
                    label("sek") {
                        addClass(MyStyle.settingsUnit)
                    }
                }
            }

            style {
                addClass(MyStyle.gridSpace)
            }
        }

        hbox {
            style {
                padding = box(40.px, 0.px, 0.px, 30.px)
            }

            button("Anuluj") {
                action {
                    close()
                }

                addClass(MyStyle.settingsButton)
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

                addClass(MyStyle.settingsButton)
            }
        }

        style {
            addClass(MyStyle.default)
        }
    }

    private fun checkTextField(textField: TextField, newValue: String, oldValue: String,
                               allowZero: Boolean) {
        try {
            val value = newValue.toInt()
            if (value < 0 || value > 60) {
                textField.text = oldValue
                showErrorDialog()
            }

            if (!allowZero && value == 0) {
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