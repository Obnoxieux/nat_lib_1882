package dev.obnx

import dev.obnx.model.BookRepository
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
fun Application.configureSerialization(repository: BookRepository) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        route("/books") {
            get {
                val books = repository.allBooks()
                call.respond(books)
            }
        }
    }
}
