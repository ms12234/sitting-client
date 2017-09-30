package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.*
import javafx.scene.control.ProgressBar
import javafx.scene.control.TabPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*
import tornadofx.control.DateTimePicker

class StatsWindowView : View("Statystki") {
    private val measurementRepository: MeasurementRepository by di()

    private var periodStartPicker: DateTimePicker by singleAssign()
    private var periodEndPicker: DateTimePicker by singleAssign()
    private var progress: ProgressBar by singleAssign()

    private var graphParent: TabPane by singleAssign()

    private var dailySittingGraph: BarChart<String, Number> by singleAssign()
    private lateinit var dailySittingDataDownload:
            Task<ObservableList<XYChart.Series<String, Number>>>

    private var breakToSittingGraph: PieChart by singleAssign()
    private lateinit var breakToSittingDataDownload: Task<ObservableList<PieChart.Data>>

    private var okToNotOkSittingGraph: PieChart by singleAssign()
    private lateinit var okToNotOkSittingDataDownload: Task<ObservableList<PieChart.Data>>

    private lateinit var heatMapDataDownload: Task<List<Double>>

    private var heatMapTop0: Rectangle by singleAssign()
    private var heatMapTop1: Rectangle by singleAssign()
    private var heatMapTop2: Rectangle by singleAssign()

    private var heatMapMiddle0: Rectangle by singleAssign()
    private var heatMapMiddle1: Rectangle by singleAssign()
    private var heatMapMiddle2: Rectangle by singleAssign()

    private var heatMapBottom0: Rectangle by singleAssign()
    private var heatMapBottom1: Rectangle by singleAssign()
    private var heatMapBottom2: Rectangle by singleAssign()

    override val root = vbox {
        hbox {
            label("Początek zakresu")
            periodStartPicker = DateTimePicker()
            add(periodStartPicker)

            label("Koniec zakresu")
            periodEndPicker = DateTimePicker()
            add(periodEndPicker)

            button("Pokaż") {
                action {
                    when (graphParent.selectionModel.selectedIndex) {
                        0 -> downloadDataForDailySittingGraph()
                        1 -> downloadDataForBreakToSittingGraph()
                        2 -> downloadDataForOkToNotOkSittingGraph()
                        3 -> downloadDataForHeatMapGraph()
                    }
                }
            }
        }

        progress = progressbar { }

        graphParent = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

            tab("Czas siedzenia") {
                content = HBox().apply {
                    dailySittingGraph = barchart("", CategoryAxis(), NumberAxis())
                }
            }

            tab("Stosunek przerw do siedzenia") {
                content = HBox().apply {
                    breakToSittingGraph = piechart("", FXCollections
                            .observableArrayList())
                }
            }

            tab("Stosunek poprawnego siedzenia do niepoprawnego") {
                content = HBox().apply {

                    okToNotOkSittingGraph = piechart("", FXCollections
                            .observableArrayList()) {
                        data("Windows", 77.62)
                        data("OS X", 9.52)
                        data("Other", 3.06)
                        data("Linux", 1.55)
                        data("Chrome OS", 0.55)
                    }
                }
            }

            tab("Mapa czujników") {
                content = VBox().apply {
                    val groupWidth = 360.0
                    val groupHeight = 450.0

                    val width = 100.0
                    val horizontalSpace = 15.0
                    val verticalSpace = 230
                    val arc = 10.0

                    vbox {
                        group {
                            rectangle(0, 0, groupWidth, groupHeight) {
                                fill = Color.TRANSPARENT
                                stroke = Color.BLACK
                                arcWidth = arc
                                arcHeight = arc

                                heatMapTop0 = rectangle(horizontalSpace,
                                        horizontalSpace,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapTop1 = rectangle(width + 2 * horizontalSpace,
                                        horizontalSpace,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapTop2 = rectangle(width * 2 + 3 * horizontalSpace,
                                        horizontalSpace,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapMiddle0 = rectangle(horizontalSpace,
                                        verticalSpace + width,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapMiddle1 = rectangle(width +
                                        horizontalSpace * 2,
                                        verticalSpace + width,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapMiddle2 = rectangle(2 * width + 3 * horizontalSpace,
                                        verticalSpace + width,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }
                            }
                        }

                        group {
                            rectangle(0, 0, groupWidth, groupHeight / 2) {
                                fill = Color.TRANSPARENT
                                stroke = Color.BLACK
                                arcWidth = arc
                                arcHeight = arc
                                val y = width + horizontalSpace / 2

                                heatMapBottom0 = rectangle(horizontalSpace,
                                        y,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapBottom1 = rectangle(2 * horizontalSpace + width,
                                        y,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }

                                heatMapBottom2 = rectangle(3 * horizontalSpace + 2 * width,
                                        y,
                                        width, width) {
                                    arcWidth = arc
                                    arcHeight = arc
                                }
                            }
                        }
                    }
                }
            }

            style {
                minWidth = 1000.px
                minHeight = 900.px
            }
        }

        style
        {
            addClass(MyStyle.default)
        }
    }

    private fun downloadDataForHeatMapGraph() {
        heatMapDataDownload = HeatMapDataDownload(periodStartPicker.dateTimeValue,
                periodEndPicker.dateTimeValue, measurementRepository)

        heatMapDataDownload.setOnSucceeded {
            runLater {
                updateHeatMapGraph(heatMapDataDownload.get())
            }
        }

        progress.bind(heatMapDataDownload.progressProperty())

        runAsync {
            heatMapDataDownload.run()
        }
    }

    private fun updateHeatMapGraph(data: List<Double>) {
        //TODO
    }

    private fun downloadDataForOkToNotOkSittingGraph() {
        okToNotOkSittingDataDownload = OkToNotOkSittingDataDownload(
                periodStartPicker.dateTimeValue, periodEndPicker.dateTimeValue,
                measurementRepository)

        okToNotOkSittingDataDownload.setOnSucceeded {
            runLater {
                updateOkToNotOkSittingGraph(okToNotOkSittingDataDownload.get())
            }
        }

        progress.bind(okToNotOkSittingDataDownload.progressProperty())

        runAsync {
            okToNotOkSittingDataDownload.run()
        }
    }

    private fun updateOkToNotOkSittingGraph(data: ObservableList<PieChart.Data>) {
        //TODO
    }

    private fun downloadDataForBreakToSittingGraph() {
        breakToSittingDataDownload = BreakToSittingDataDownload(
                periodStartPicker.dateTimeValue,
                periodEndPicker.dateTimeValue, measurementRepository)

        breakToSittingDataDownload.setOnSucceeded {
            runLater {
                updateBreakToSittingGraph(breakToSittingDataDownload.get())
            }
        }

        progress.bind(breakToSittingDataDownload.progressProperty())

        runAsync {
            breakToSittingDataDownload.run()
        }
    }

    private fun updateBreakToSittingGraph(data: ObservableList<PieChart.Data>) {
        //TODO
        breakToSittingGraph.data = breakToSittingDataDownload.get()

    }

    private fun downloadDataForDailySittingGraph() {
        dailySittingDataDownload =
                DailySittingDataDownload(periodStartPicker.dateTimeValue,
                        periodEndPicker.dateTimeValue, measurementRepository)

        dailySittingDataDownload.setOnSucceeded {
            runLater {
                updateDailySittingGraph(dailySittingDataDownload.get())
            }
        }

        progress.bind(dailySittingDataDownload.progressProperty())

        runAsync {
            dailySittingDataDownload.run()
        }
    }

    private fun updateDailySittingGraph(data: ObservableList<XYChart.Series<String, Number>>) {
        //TODO
    }
}