package com.ms.sittingclient.settings_window

import com.google.gson.GsonBuilder
import java.nio.file.Files
import java.nio.file.Path

class SettingsIo(private val pathToFile: Path) {
    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun read(): Settings? {
        if (fileNotExists()) {
            return null
        }

        val json = String(Files.readAllBytes(pathToFile))
        return gson.fromJson(json, Settings::class.java)
    }

    fun save(settings: Settings) {
        val json = gson.toJson(settings)
        Files.write(pathToFile, json.toByteArray())
    }

    private fun fileNotExists() = Files.notExists(pathToFile)
}