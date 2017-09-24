package com.ms.sittingclient.settings_window

class Settings(var breakMinutes: Int, var breakSeconds: Int,
               var workMinutes: Int, var workSeconds: Int) {

    constructor() : this(3, 0, 30, 0)
}