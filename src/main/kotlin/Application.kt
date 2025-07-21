package dev.obnx

import dev.obnx.model.FakeBookRepository
import io.ktor.server.application.*
import kotlinx.serialization.ExperimentalSerializationApi

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@ExperimentalSerializationApi
fun Application.module() {
    val bookRepository = FakeBookRepository()

    configureFrameworks()
    configureSerialization(bookRepository)
    configureDatabases()
    configureMonitoring()
    configureSecurity()
    configureHTTP()
    configureRouting()
    configureTemplating()
}
