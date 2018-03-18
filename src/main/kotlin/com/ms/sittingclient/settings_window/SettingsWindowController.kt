package com.ms.sittingclient.settings_window

import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import tornadofx.*

@Component
class SettingsWindowController(private val settingsIo: SettingsIo,
                               private val soundNotifiers: SoundNotifiers) : Controller() {

    private val LOG = LoggerFactory.getLogger(this.javaClass.name)
    val visualReaction = SimpleBooleanProperty()
    val soundReaction = SimpleBooleanProperty()
    val soundReactionType = SimpleStringProperty()
    val reactionDelay = SimpleStringProperty()
    val baselineMonitoringTime = SimpleStringProperty()
    val learningMonitoringTime = SimpleStringProperty()
    val reviewMonitoringTime = SimpleStringProperty()

    fun getSoundNotifierNames(): List<String> {
        return soundNotifiers.values.keys.toList()
    }

    fun assignValues(settings: Settings) {
        LOG.info("Wpisywanie ustawień do pól")
        visualReaction.setValue(settings.visualReaction)
        soundReaction.setValue(settings.soundReaction)
        soundReactionType.setValue(soundNotifiers.values.inverseBidiMap().get(settings.soundReactionType))
        reactionDelay.setValue(settings.reactionDelayInSeconds.toString())
        baselineMonitoringTime.setValue(settings.baselineMonitoringTimeInMinutes.toString())
        learningMonitoringTime.setValue(settings.learningTimeInMinutes.toString())
        reviewMonitoringTime.setValue(settings.reviewTimeInMinutes.toString())
    }

    fun saveToFile() {
        val settingsFromProperties = Settings(visualReaction.get(), soundReaction.get(),
                soundNotifiers.values.get(soundReactionType.get())!!, reactionDelay.get().toInt(),
                baselineMonitoringTime.get().toInt(), learningMonitoringTime.get().toInt(),
                reviewMonitoringTime.get().toInt())
        LOG.info("Zapisywanie ustawień")
        settingsIo.save(settingsFromProperties)
    }

    fun readSettings(): Settings {
        LOG.info("Czytanie ustawień")
        return settingsIo.read() ?: Settings()
    }
}