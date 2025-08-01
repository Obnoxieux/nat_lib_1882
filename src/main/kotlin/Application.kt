package dev.obnx

import dev.obnx.model.PostgresAuthorRepository
import dev.obnx.model.PostgresBookRepository
import dev.obnx.model.PostgresEndowmentRepository
import dev.obnx.model.PostgresGenreRepository
import io.ktor.server.application.*
import io.ktor.server.resources.*
import kotlinx.serialization.ExperimentalSerializationApi

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@ExperimentalSerializationApi
fun Application.module() {
    val bookRepository = PostgresBookRepository()
    val authorRepository = PostgresAuthorRepository()
    val genreRepository = PostgresGenreRepository()
    val endowmentRepository = PostgresEndowmentRepository()

    install(Resources)

    configureFrameworks()
    configureSerialization(
        bookRepository,
        authorRepository,
        genreRepository,
        endowmentRepository
    )
    configureDatabases()
    configureMonitoring()
    configureSecurity()
    configureHTTP()
    configureRouting()
    configureTemplating()
    configureCaching()
}
