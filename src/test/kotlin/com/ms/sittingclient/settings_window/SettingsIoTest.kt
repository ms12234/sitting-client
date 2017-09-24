package com.ms.sittingclient.settings_window

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.file.Paths

class SettingsIoTest {
    @Test
    fun saveAndReadDefaultValues() {
        val settingsIo = SettingsIo(Paths.get("existing.txt"))
        settingsIo.save(Settings())
        assertTrue(settingsIo.read() != null)
    }

    @Test
    fun saveAndReadNotDefaultValues() {
        val settingsIo = SettingsIo(Paths.get("existing.txt"))
        val settings = Settings(55, 0, 0, 0)
        settingsIo.save(settings)
        assertTrue(settingsIo.read() != null)
        assertEquals(55, settingsIo.read()!!.breakMinutes)
    }

    @Test
    fun readNotExisting() {
        val settingsIo = SettingsIo(Paths.get("notExisting.txt"))
        assertTrue(settingsIo.read() == null)
    }
}