package com.ms.sittingclient.settings_window

import javafx.beans.property.SimpleIntegerProperty
import org.springframework.stereotype.Component
import tornadofx.*

@Component
class SettingsWindowController(private val settingsIo: SettingsIo) : Controller() {
    val breakMinutes = SimpleIntegerProperty()
    val breakSeconds = SimpleIntegerProperty()
    val workMinutes = SimpleIntegerProperty()
    val workSeconds = SimpleIntegerProperty()

    fun assignValues(settings: Settings) {
        breakMinutes.set(settings.breakMinutes)
        breakSeconds.set(settings.breakSeconds)
        workMinutes.set(settings.workMinutes)
        workSeconds.set(settings.workSeconds)
    }

    fun saveToFile() {
        val settingsFromProperties = Settings(breakMinutes.value, breakSeconds.value,
                workMinutes.value, workSeconds.value)
        settingsIo.save(settingsFromProperties)
    }

    fun readSettings(): Settings {
        return settingsIo.read() ?: Settings()
    }
}