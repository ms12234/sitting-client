package com.ms.sittingclient.repository

import feign.Feign
import feign.Logger
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {

    @Bean
    fun getRepository() {
        Feign.builder()
                .encoder(GsonEncoder())
                .decoder(GsonDecoder())
                .logger(Logger.JavaLogger())
                .logLevel(Logger.Level.BASIC)
                .target(MeasurementRepository::class.java, "http://localhost:8080")
    }
}
