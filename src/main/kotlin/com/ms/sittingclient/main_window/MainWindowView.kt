package com.ms.sittingclient.main_window

import com.ms.sittingclient.MyStyle
import com.ms.sittingclient.repository.Measurement
import com.ms.sittingclient.repository.MeasurementRepository
import com.ms.sittingclient.settings_window.SettingsWindowView
import com.ms.sittingclient.stats_window.StatsWindowView
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.scene.image.ImageView
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import java.util.concurrent.ExecutorService

class MainWindowView : View("Podsumowanie"), MeasurementObserver {
    private val executorService: ExecutorService by di()
    private val repository: MeasurementRepository by di()
    private val timeCounter: TimeCounter by di()

    private val okImage = resources.image("/okImage.png")
    private val notOkImage = resources.image("/notOkImage.png")
    private val errorImage = resources.image("/errorImage.png")

    private var statusImageView: ImageView by singleAssign()
    private var currentPeriodLabel: Label by singleAssign()
    private var waitingAmountLabel: Label by singleAssign()
    private var waitingProgressBar: ProgressBar by singleAssign()

    override val root = hbox {
        spacing = 30.0

        vbox {
            spacing = 30.0

            vbox {
                label("Ocena aktualnej pozycji")
                statusImageView = imageview {
                    val size = 250.0
                    fitHeight = size
                    fitWidth = size
                }
            }

            button("Statystki").action {
                find<StatsWindowView>().openModal()
            }
        }

        line(0, 0, 0, 330)

        vbox {
            spacing = 15.0

            hbox {
                label("Aktualnie trwa ")
                currentPeriodLabel = label {
                    font = Font.font("Verdana", FontWeight.BOLD, 18.0)
                    bind(timeCounter.currentPeriodName)
                }
            }

            hbox {
                label("Pozostało ")
                waitingAmountLabel = label {
                    font = Font.font("Verdana", FontWeight.BOLD, 18.0)
                    bind(timeCounter.timeLeft)
                }
            }

            waitingProgressBar = progressbar {
                bind(timeCounter.progress)
                minWidth = 250.0
            }

            button {
                textProperty().bind(timeCounter.nextPeriodName)
                action {
                    timeCounter.nextPeriod()
                }
            }

            button("Ustawienia").action {
                SettingsWindowView().openModal()
            }
        }

        style {
            addClass(MyStyle.default)
        }
    }

    override fun onError() {
        statusImageView.image = errorImage
    }

    override fun onNext(measurement: Measurement) {
        if (measurement.grade == 1.0F) {
            statusImageView.image = okImage
        } else {
            statusImageView.image = notOkImage
        }
    }

    init {
        executorService.submit(MeasurementsThread(repository, this))
        timeCounter.init()
    }
}