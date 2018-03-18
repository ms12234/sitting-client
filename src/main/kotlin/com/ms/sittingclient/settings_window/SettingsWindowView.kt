package com.ms.sittingclient.settings_window

import com.ms.sittingclient.MyStyle
import com.ms.sittingclient.SettingsChangedEvent
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import javafx.scene.control.CheckBox
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import tornadofx.*

class SettingsWindowView : View("Ustawienia") {

    private val controller: SettingsWindowController by di()
    private var visualReaction: CheckBox by singleAssign()
    private var soundReactionCheckBox: CheckBox by singleAssign()
    private var soundReactionComboBox: ComboBox<String> by singleAssign()
    private val soundReactionOptions =
            FXCollections.observableArrayList<String>(controller.getSoundNotifierNames())
    private var reactionDelay: TextField by singleAssign()
    private var baselineMonitoringTime: TextField by singleAssign()
    private var learningMonitoringTime: TextField by singleAssign()
    private var reviewMonitoringTime: TextField by singleAssign()

    override val root = vbox {
        gridpane {
            row {
                label("Wizualne informowanie (strzałki)")
                visualReaction = checkbox {
                    selectedProperty().bindBidirectional(controller.visualReaction)
                }
            }

            row {
                label("Głosowe informowanie")
                soundReactionCheckBox = checkbox {
                    selectedProperty().bindBidirectional(controller.soundReaction)
                }
                val observableTrue = SimpleBooleanProperty(true)
                soundReactionComboBox = combobox {
                    enableWhen { soundReactionCheckBox.selectedProperty().isEqualTo(observableTrue) }
                    items = soundReactionOptions
                }
                soundReactionComboBox.valueProperty().bindBidirectional(controller.soundReactionType)
            }

            row {
                label("Opóźnienie informowania (sek)")
                reactionDelay = textfield {
                    textProperty().bindBidirectional(controller.reactionDelay)
                }
            }

            row {
                label("Czas trwania etapu I (wyznaczanie normy) (min)")
                baselineMonitoringTime = textfield {
                    textProperty().bindBidirectional(controller.baselineMonitoringTime)
                }
            }

            row {
                label("Czas trwania etapu II (nauka) (min)")
                learningMonitoringTime = textfield {
                    textProperty().bindBidirectional(controller.learningMonitoringTime)
                }
            }

            row {
                label("Czas trwania etapu III (sprawdzenie) (min)")
                reviewMonitoringTime = textfield {
                    textProperty().bindBidirectional(controller.reviewMonitoringTime)
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