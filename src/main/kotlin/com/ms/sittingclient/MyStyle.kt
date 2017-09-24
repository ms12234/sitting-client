package com.ms.sittingclient

import tornadofx.*

class MyStyle : Stylesheet() {
    companion object {
        val default by cssclass()
        val gridSpace by cssclass()
        val settingInput by cssclass()
        val settingsButton by cssclass()
        val settingsUnit by cssclass()
    }

    init {
        val defaultFontSize = 17.px

        default {
            padding = box(30.px)
            fontSize = defaultFontSize
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
        }

        settingsUnit {
            padding = box(5.px, 0.px, 0.px, 0.px)
        }
    }
}