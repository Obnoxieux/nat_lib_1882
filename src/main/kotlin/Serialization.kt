package dev.obnx

import dev.obnx.model.BookRepository
import dev.obnx.model.Page
import dev.obnx.resources.Books
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.jte.JteContent
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.Resources
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
fun Application.configureSerialization(repository: BookRepository) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get<Books> { book ->
            val books = repository.allBooks()

            val acceptHeader = call.request.headers["Accept"]

            if (acceptHeader?.contains("application/json") == true) {
                call.respond(books)
            } else {
                val page = Page(
                    title = "Catalogue Page",
                    description = "Browse full 1882 catalogue of books in the Damascus Public Library"
                )
                call.respond(JteContent("books.kte", mapOf("books" to books, "page" to page)))
            }
        }
    }
}
