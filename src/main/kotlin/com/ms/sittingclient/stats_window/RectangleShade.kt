package com.ms.sittingclient.stats_window

import javafx.scene.paint.Paint
import tornadofx.*

class RectangleShade {
    fun assign(ok: Int, total: Int): Paint {
        val value = ok.toDouble() / total.toDouble()

        return when {
            value >= 0.9 -> c("#031727")
            value >= 0.8 -> c("#126872")
            value >= 0.7 -> c("#0B877D")
            value >= 0.6 -> c("#18C29C")
            else -> c("#88F9D4")
        }
    }
}