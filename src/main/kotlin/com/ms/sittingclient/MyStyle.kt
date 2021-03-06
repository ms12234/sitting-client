package com.ms.sittingclient

import tornadofx.*

class MyStyle : Stylesheet() {
    companion object {
        val default by cssclass()
        val gridSpace by cssclass()
        val settingInput by cssclass()
        val settingsButton by cssclass()
        val settingsUnit by cssclass()
        val heatMapRectangle by cssclass()
    }

    init {
        val defaultFontSize = 16.px

        default {
            padding = box(30.px)
            fontSize = defaultFontSize
            backgroundColor += c(255, 255, 255)
        }

        gridSpace {
            hgap = 25.px
            vgap = 25.px
        }

        settingInput {
            maxWidth = 50.px
        }

        settingsButton {
            fontSize = defaultFontSize
            minWidth = 150.px
            minHeight = 30.px
        }

        settingsUnit {
            padding = box(6.px, 0.px, 0.px, 0.px)
        }

        heatMapRectangle {
            backgroundColor += c(120, 100, 100, 1.0)
        }
    }
}