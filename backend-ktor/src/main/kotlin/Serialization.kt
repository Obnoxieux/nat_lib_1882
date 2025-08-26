package dev.obnx

import dev.obnx.model.*
import dev.obnx.resources.Authors
import dev.obnx.resources.Books
import dev.obnx.resources.Endowments
import dev.obnx.resources.Genres
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.jte.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
fun Application.configureSerialization(
    bookRepository: BookRepository,
    authorRepository: AuthorRepository,
    genreRepository: GenreRepository,
    endowmentRepository: EndowmentRepository
) {
    install(ContentNegotiation) {
        json()
    }

    routing {
        get<Books> { book ->
            val books = bookRepository.findFilteredBooks(filter = book)

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

        get<Books.Id> { book ->
            val result = bookRepository.bookByID(book.id)

            if (result == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(result)
            }
        }

        get<Authors> { _ ->
            val authors = authorRepository.allAuthors()

            val acceptHeader = call.request.headers["Accept"]

            if (acceptHeader?.contains("application/json") == true) {
                call.respond(authors)
            } else {
                val page = Page(
                    title = "Authors Page",
                    description = "Browse all authors in the Damascus Public Library catalogue"
                )
                call.respond(JteContent("authors.kte", mapOf("authors" to authors, "page" to page)))
            }
        }

        get<Authors.Id> { author ->
            val result = authorRepository.authorByID(author.id)

            if (result == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(result)
            }
        }

        get<Genres> { _ ->
            val genres = genreRepository.allGenres()

            val acceptHeader = call.request.headers["Accept"]

            if (acceptHeader?.contains("application/json") == true) {
                call.respond(genres)
            } else {
                val page = Page(
                    title = "Genres Page",
                    description = "Browse all genres in the Damascus Public Library catalogue"
                )
                call.respond(JteContent("genres.kte", mapOf("genres" to genres, "page" to page)))
            }
        }

        get<Endowments> { _ ->
            val endowments = endowmentRepository.allEndowments()

            val acceptHeader = call.request.headers["Accept"]

            if (acceptHeader?.contains("application/json") == true) {
                call.respond(endowments)
            } else {
                val page = Page(
                    title = "Endowments Page",
                    description = "Browse all endowments in the Damascus Public Library catalogue"
                )
                call.respond(JteContent("endowments.kte", mapOf("endowments" to endowments, "page" to page)))
            }
        }
    }
}
