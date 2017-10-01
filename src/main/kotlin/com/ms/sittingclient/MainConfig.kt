package com.ms.sittingclient

import com.ms.sittingclient.settings_window.SettingsIo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.nio.file.Paths

@Configuration
open class MainConfig {
    @Bean
    fun getSettingsIo(): SettingsIo {
        return SettingsIo(Paths.get("settings.json"))
    }

    @Bean
    fun insertFakeData(): Boolean {
        return true
    }
}