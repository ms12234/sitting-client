package com.ms.sittingclient

import tornadofx.*

class MyStyle : Stylesheet() {
    companion object {
        val default by cssclass()
        val gridSpace by cssclass()
    }

    init {
        val defaultFontSize = 17.px

        default {
            padding = box(20.px)
            fontSize = defaultFontSize
        }

        gridSpace {
            hgap = 15.px
            vgap = 15.px
        }
    }
}