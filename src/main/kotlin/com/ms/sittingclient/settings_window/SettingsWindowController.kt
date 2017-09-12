package com.ms.sittingclient.settings_window

import javafx.beans.property.SimpleIntegerProperty
import org.springframework.stereotype.Component
import tornadofx.*

@Component
class SettingsWindowController : Controller() {
    val breakMinutes = SimpleIntegerProperty()
    val breakSeconds = SimpleIntegerProperty()
    val workMinutes = SimpleIntegerProperty()
    val workSeconds = SimpleIntegerProperty()

    fun loadFromFile() {
        //TODO
    }

    fun saveToFile() {
        //TODO
    }
}