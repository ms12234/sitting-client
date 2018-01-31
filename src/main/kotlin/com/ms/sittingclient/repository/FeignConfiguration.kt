package com.ms.sittingclient.repository

import feign.Feign
import feign.Logger
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfiguration {

    @Bean
    fun getRepository(): MeasurementRepository {
        return Feign.builder()
                .encoder(JacksonEncoder())
                .decoder(JacksonDecoder())
                .logger(Logger.JavaLogger())
                .logLevel(Logger.Level.BASIC)
                .target(MeasurementRepository::class.java, "http://192" +
                        ".168.0.105:8080")
    }
}
