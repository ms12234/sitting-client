package com.ms.sittingclient.stats_window

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.springframework.stereotype.Component
import tornadofx.*
import java.time.LocalDateTime

@Component
class StatsWindowController : Controller() {
    val start = SimpleObjectProperty<LocalDateTime>(LocalDateTime.now())
    val end = SimpleObjectProperty<LocalDateTime>(LocalDateTime.now().minusDays(1))
    val graph = SimpleStringProperty()
    val graphs: ObservableList<String> = FXCollections.observableArrayList()
}