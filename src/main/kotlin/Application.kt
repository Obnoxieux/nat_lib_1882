package dev.obnx

import dev.obnx.model.FakeBookRepository
import io.ktor.server.application.*
import io.ktor.server.resources.Resources
import kotlinx.serialization.ExperimentalSerializationApi

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@ExperimentalSerializationApi
fun Application.module() {
    val bookRepository = FakeBookRepository()

    install(Resources)

    configureFrameworks()
    configureSerialization(bookRepository)
    configureDatabases()
    configureMonitoring()
    configureSecurity()
    configureHTTP()
    configureRouting()
    configureTemplating()
    configureCaching()
}
