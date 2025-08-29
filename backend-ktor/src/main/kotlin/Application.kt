package dev.obnx

import dev.obnx.model.PostgresAuthorRepository
import dev.obnx.model.PostgresBookRepository
import dev.obnx.model.PostgresEndowmentRepository
import dev.obnx.model.PostgresGenreRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*
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

    install(DefaultHeaders) {
        header(name = HttpHeaders.Server, value = "Ktor (Kotlin)")
        header(name = HttpHeaders.AccessControlAllowOrigin, value = "*")
    }

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
