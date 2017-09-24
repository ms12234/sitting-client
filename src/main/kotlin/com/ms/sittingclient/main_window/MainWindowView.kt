package com.ms.sittingclient.main_window

import com.ms.sittingclient.MyStyle
import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.MeasurementRepository
import com.ms.sittingclient.settings_window.SettingsWindowView
import com.ms.sittingclient.stats_window.StatsWindowView
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.image.ImageView
import tornadofx.*
import java.util.concurrent.ExecutorService

class MainWindowView : View("Podsumowanie"), MeasurementObserver {
    private val executorService: ExecutorService by di()
    private val repository: MeasurementRepository by di()
    private val okImage = resources.image("/okImage.png")
    private val notOkImage = resources.image("/notOkImage.png")

    private var statusImageView: ImageView by singleAssign()
    private var waitingForLabel: Label by singleAssign()
    private var waitingAmountLabel: Label by singleAssign()
    private var waitingProgressBar: ProgressBar by singleAssign()

    override val root = hbox {
        vbox {
            label("Ocena aktualnej pozycji")
            statusImageView = imageview()
            button("Statystki") {
                action {
                    find<StatsWindowView>().openModal()
                }
            }
        }

        vbox {
            label("Pozostały czas do konca")
            waitingForLabel = label()
            waitingAmountLabel = label()
            waitingProgressBar = progressbar()

            button("Ustawienia") {
                action {
                    SettingsWindowView().openModal()
                }
            }
        }

        style {
            addClass(MyStyle.default)
        }
    }

    override fun onError(error: Error) {
        when (error) {
            Error.COULD_NOT_CONNECT -> {

            }
            Error.NO_MEASUREMENTS_IN_LAST_SECONDS -> {

            }
            Error.SLEEPING_INTERRUPTED -> {

            }
        }
    }

    override fun onNext(measurement: Measurement) {
        if (measurement.grade > 0) {
            //ok image
        } else {
            //not ok image
        }
    }

    init {
        executorService.submit(MeasurementsThread(repository, this))
    }
}