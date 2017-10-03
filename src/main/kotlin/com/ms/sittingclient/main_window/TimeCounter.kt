package com.ms.sittingclient.main_window

import com.ms.sittingclient.settings_window.SettingsIo
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import org.springframework.stereotype.Component
import tornadofx.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

@Component
class TimeCounter(private val settingsIo: SettingsIo) : TimerTask() {
    private var currentSeconds: AtomicInteger = AtomicInteger(0)
    private var breakTime: AtomicInteger = AtomicInteger(0)
    private var workTime: AtomicInteger = AtomicInteger(0)
    var currentPeriod = CurrentPeriod.WORK

    val currentPeriodName: SimpleStringProperty = SimpleStringProperty("")
    val nextPeriodName: SimpleStringProperty = SimpleStringProperty("")
    val timeLeft: SimpleStringProperty = SimpleStringProperty("")
    val progress: SimpleDoubleProperty = SimpleDoubleProperty(0.0)

    fun init() {
        val settings = settingsIo.read()
        breakTime.set(settings.breakMinutes * 60 + settings.breakSeconds)
        workTime.set(settings.workMinutes * 60 + settings.workSeconds)
        updatePeriod()
        val timer = Timer(true)
        timer.scheduleAtFixedRate(this, 0, 1000)
    }

    override fun run() {
        val incrementedSeconds = currentSeconds.incrementAndGet()
        synchronized(currentPeriod) {
            when (currentPeriod) {

                CurrentPeriod.WORK -> {
                    if (currentSeconds.get() == workTime.get()) {
                        changePeriod()

                        runLater {
                            updatePeriod()
                        }
                    }
                }
                CurrentPeriod.BREAK -> {
                    if (currentSeconds.get() == breakTime.get()) {
                        changePeriod()

                        runLater {
                            updatePeriod()
                        }
                    }
                }
            }
        }

        runLater {
            updateTimeLeft()
            updateProgress()
        }
    }

    private fun updatePeriod() {
        synchronized(currentPeriod) {
            when (currentPeriod) {

                CurrentPeriod.WORK -> {
                    currentPeriodName.set("PRACA")
                    nextPeriodName.set("Rozpocznij przerwę")
                }
                CurrentPeriod.BREAK -> {
                    currentPeriodName.set("PRZERWA")
                    nextPeriodName.set("Rozpocznij pracę")
                }
            }
        }
    }

    private fun updateProgress() {
        synchronized(currentPeriod) {
            when (currentPeriod) {
                CurrentPeriod.WORK -> {
                    progress.set(currentSeconds.toDouble() / workTime.toDouble())
                }
                CurrentPeriod.BREAK -> {
                    progress.set(currentSeconds.toDouble() / breakTime.toDouble())
                }
            }
        }
    }

    private fun updateTimeLeft() {
        synchronized(currentPeriod) {
            when (currentPeriod) {
                CurrentPeriod.WORK -> {
                    timeLeft.set(convertSecondsToText(workTime.get() -
                            currentSeconds.get()))
                }
                CurrentPeriod.BREAK -> {
                    timeLeft.set(convertSecondsToText(breakTime.get() -
                            currentSeconds.get()))
                }
            }
        }
    }

    private fun convertSecondsToText(i: Int): String? {
        val minutes = TimeUnit.SECONDS.toMinutes(i.toLong())
        val seconds = i - minutes * 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun changePeriod() {
        synchronized(currentPeriod) {
            if (currentPeriod == CurrentPeriod.WORK) {
                currentPeriod = CurrentPeriod.BREAK
            } else {
                currentPeriod = CurrentPeriod.WORK
            }
        }

        currentSeconds.set(0)
    }

    fun nextPeriod() {
        synchronized(currentPeriod) {
            when (currentPeriod) {
                CurrentPeriod.WORK -> currentSeconds.set(workTime.get() - 1)
                CurrentPeriod.BREAK -> currentSeconds.set(breakTime.get() - 1)
            }
        }
    }
}