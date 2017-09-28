package com.ms.sittingclient.stats_window

import com.ms.sittingclient.MyStyle
import javafx.collections.FXCollections
import javafx.scene.chart.BarChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.PieChart
import javafx.scene.control.TabPane
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.shape.Rectangle
import tornadofx.*
import tornadofx.control.DateTimePicker

class StatsWindowView : View("Statystki") {
    private val controller: StatsWindowController by di()

    private var periodStartPicker: DateTimePicker by singleAssign()
    private var periodEndPicker: DateTimePicker by singleAssign()

    private var graphParent: TabPane by singleAssign()
    private var dailySittingGraph: BarChart<String, Number>
            = barchart("", CategoryAxis(), NumberAxis())
    private var breakToSittingGraph: PieChart = piechart("", FXCollections
            .observableArrayList())
    private var okToNotOkSittingGraph: PieChart by singleAssign()

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
                        0 -> {
                            runAsyncWithProgress {
                                controller.showGraphDailySitting(dailySittingGraph,
                                        periodStartPicker.dateTimeValue,
                                        periodEndPicker.dateTimeValue)
                            } ui {

                            }

                        }
                        1 -> {
                            controller.showGraphBreakToSitting(
                                    breakToSittingGraph, periodStartPicker
                                    .dateTimeValue, periodEndPicker.dateTimeValue)
                        }
                        2 -> {
                            controller.showGraphOkToNotOkSitting(
                                    okToNotOkSittingGraph, periodStartPicker
                                    .dateTimeValue, periodEndPicker.dateTimeValue
                            )
                        }
                        3 -> {
                            controller.showHeatMap(heatMapTop0, heatMapTop1,
                                    heatMapTop2, heatMapMiddle0, heatMapMiddle1,
                                    heatMapMiddle2, heatMapBottom0, heatMapBottom1,
                                    heatMapBottom2)
                        }
                    }
                }
            }
        }
        graphParent = tabpane {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

            tab("Czas siedzenia") {
                content = HBox().apply {
                    label("Hasdf")
                    label("Hasdf")

                    add(dailySittingGraph)
                }
            }

            tab("Stosunek przerw do siedzenia") {
                content = HBox().apply {
                    label("Hasdf")
                    label("Hasdf")
                    add(breakToSittingGraph)
                }
            }

            tab("Stosunek poprawnego siedzenia do niepoprawnego") {
                content = HBox().apply {
                    label("Hasdf")
                    label("Hasdf")

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
                    label("Hasdf")
                    label("Hasdf")


                    hbox {
                        style {
                            minHeight = 300.px
                            minWidth = 300.px
                        }

                        heatMapTop0 = rectangle(0, 0, 100, 100) {
                            addClass(MyStyle.heatMapRectangle)
                        }

                        heatMapTop1 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }

                        heatMapTop2 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }
                    }

                    hbox {
                        heatMapMiddle0 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }

                        heatMapMiddle1 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }

                        heatMapMiddle2 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }
                    }

                    hbox {
                        heatMapBottom0 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }

                        heatMapBottom1 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }

                        heatMapBottom2 = rectangle {
                            addClass(MyStyle.heatMapRectangle)
                        }
                    }
                }
            }

            style {
                minWidth = 1000.px
                minHeight = 900.px
            }
        }

        style {
            addClass(MyStyle.default)
        }
    }
}