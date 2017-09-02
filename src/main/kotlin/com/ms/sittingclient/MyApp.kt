package com.ms.sittingclient

import javafx.application.Application
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import tornadofx.*
import kotlin.reflect.KClass

@SpringBootApplication
class MyApp : App(MainView::class) {
    override fun init() {
        super.init()
        val applicationContext = SpringApplication.run(MyApp::class.java)
        FX.dicontainer = object : DIContainer {
            override fun <T : Any> getInstance(type: KClass<T>): T = applicationContext.getBean(type.java)
            override fun <T : Any> getInstance(type: KClass<T>, name: String): T = applicationContext.getBean(type.java, name)
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(MyApp::class.java, *args)
}