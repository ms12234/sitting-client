package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import com.ms.sittingclient.repository.MeasurementRepository
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.concurrent.Task
import javafx.scene.chart.*
import javafx.scene.control.ProgressBar
import javafx.scene.control.TabPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.shape.Rectangle
import tornadofx.*
import tornadofx.control.DateTimePicker
import java.time.LocalDateTime

class StatsWindowView : View("Statystki") {
    private val insertFakeData: Boolean by di()
    private val measurementRepository: MeasurementRepository by di()

    private var periodStartPicker: DateTimePicker = DateTimePicker()
    private var periodEndPicker: DateTimePicker = DateTimePicker()
    private var progress: ProgressBar by singleAssign()

    private var graphParent: TabPane by singleAssign()

    private var dailySittingGraph: LineChart<String, Number> by singleAssign()
    private lateinit var dailySittingDataDownload:
            Task<ObservableList<XYChart.Series<String, Number>>>

    private var breakToSittingGraph: PieChart by singleAssign()
    private lateinit var breakToSittingDataDownload: Task<ObservableList<PieChart.Data>>

    private var okToNotOkSittingGraph: PieChart by singleAssign()
    private lateinit var okToNotOkSittingDataDownload: Task<ObservableList<PieChart.Data>>

    private lateinit var heatMapDataDownload: Task<List<Paint>>

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
        addClass(MyStyle.default)

        spacing = 30.0

        hbox {
            val topPadding = 5.px
            spacing = 70.0

            hbox {
                label("Początek zakresu") {
                    style {
                        padding = box(topPadding, 5.px, 0.px, 0.px)
                    }
                }
                periodStartPicker.dateTimeValue = LocalDateTime.now().minusDays(1)
                add(periodStartPicker)
            }

            hbox {
                label("Koniec zakresu") {
                    style {
                        padding = box(topPadding, 5.px, 0.px, 0.px)
                    }
                }
                periodEndPicker.dateTimeValue = LocalDateTime.now()
                add(periodEndPicker)
            }

            hbox {
                val width = 150.0

                button("Pokaż") {
                    minWidth = width

                    action {
                        when (graphParent.selectionModel.selectedIndex) {
                            0 -> downloadDataForDailySittingGraph()
                            1 -> downloadDataForBreakToSittingGraph()
                            2 -> downloadDataForOkToNotOkSittingGraph()
                            3 -> downloadDataForHeatMapGraph()
                        }
                    }
                }

                progress = progressbar(100.0) {
                    minWidth = width
                    minHeight = 34.0
                    visibleProperty().bind(progressProperty().isNotEqualTo(100))
                }
            }
        }

        graphParent = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            val graphPadding = box(20.px, 0.px, 0.px, 0.px)
            val graphScale = 1.3

            tab("Czas siedzenia") {
                content = VBox().apply {
                    val yAxis = NumberAxis()
                    yAxis.label = "Godz."

                    val xAxis = CategoryAxis()
                    xAxis.animated = false

                    dailySittingGraph = linechart("", xAxis, yAxis) {
                        style {
                            padding = graphPadding
                        }
                    }
                }
            }

            tab("Stosunek przerw do siedzenia") {
                content = VBox().apply {
                    breakToSittingGraph = piechart("", FXCollections
                            .observableArrayList()) {
                        scaleX = graphScale
                        scaleY = graphScale
                        style {
                            padding = graphPadding
                        }
                    }
                }
            }

            tab("Stosunek poprawnego siedzenia do niepoprawnego") {
                content = VBox().apply {
                    okToNotOkSittingGraph = piechart("", FXCollections
                            .observableArrayList()) {
                        scaleX = graphScale
                        scaleY = graphScale
                        style {
                            padding = graphPadding
                        }
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
                minWidth = 1200.px
                minHeight = 700.px
            }
        }
    }

    private fun downloadDataForHeatMapGraph() {
        heatMapDataDownload = HeatMapDataDownload(periodStartPicker.dateTimeValue,
                periodEndPicker.dateTimeValue, measurementRepository, insertFakeData)

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

    private fun updateHeatMapGraph(data: List<Paint>) {
        heatMapTop0.fill = data[0]
        heatMapTop1.fill = data[1]
        heatMapTop2.fill = data[2]

        heatMapMiddle0.fill = data[3]
        heatMapMiddle1.fill = data[4]
        heatMapMiddle2.fill = data[5]

        heatMapBottom0.fill = data[6]
        heatMapBottom1.fill = data[7]
        heatMapBottom2.fill = data[8]
    }

    private fun downloadDataForOkToNotOkSittingGraph() {
        okToNotOkSittingDataDownload = OkToNotOkSittingDataDownload(
                periodStartPicker.dateTimeValue, periodEndPicker.dateTimeValue,
                measurementRepository, PieChartUtils(),
                insertFakeData)

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
        okToNotOkSittingGraph.data = data
    }

    private fun downloadDataForBreakToSittingGraph() {
        breakToSittingDataDownload = BreakToSittingDataDownload(
                periodStartPicker.dateTimeValue,
                periodEndPicker.dateTimeValue, measurementRepository,
                PieChartUtils(), insertFakeData)

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
        breakToSittingGraph.data = data
    }

    private fun downloadDataForDailySittingGraph() {
        dailySittingDataDownload =
                DailySittingDataDownload(periodStartPicker.dateTimeValue,
                        periodEndPicker.dateTimeValue, measurementRepository,
                        insertFakeData)

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
        dailySittingGraph.data = data
    }
}