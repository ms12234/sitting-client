package com.ms.sittingclient.stats_window

import org.springframework.stereotype.Component

@Component
class Graphs {
    val get: Map<String, Graph> = mapOf(
            "Ilość dziennego siedzenia" to Graph.DAILY_SITTING,
            "Stosunek czasu przerw do czasu siedzenia" to Graph.BREAK_TO_SITING_RATIO,
            "Stosunek poprawnego siedzenia do niepoprawnego" to Graph
                    .OK_TO_NOT_OK_POSTURE_RATIO,
            "Mapa czujników" to Graph.HEAT_MAP
    )
}