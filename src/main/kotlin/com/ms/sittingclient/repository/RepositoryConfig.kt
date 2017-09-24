package com.ms.sittingclient.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Configuration
open class RepositoryConfig {

    @Bean
    fun getExecutorService(): ExecutorService {
        return Executors.newFixedThreadPool(2)
    }
}
