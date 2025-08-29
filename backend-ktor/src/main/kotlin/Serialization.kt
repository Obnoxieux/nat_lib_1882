package dev.obnx

import dev.obnx.model.*
import dev.obnx.resources.Authors
import dev.obnx.resources.Books
import dev.obnx.resources.Endowments
import dev.obnx.resources.Genres
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
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
            call.respond(books)
        }

        get<Books.Id> { book ->
            val result = bookRepository.bookByID(book.id)

            if (result == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(error = "NOT_FOUND", message = "Book with id ${book.id} not found")
                )
            } else {
                call.respond(result)
            }
        }

        get<Authors> { _ ->
            val authors = authorRepository.allAuthors()
            call.respond(authors)
        }

        get<Authors.Id> { author ->
            val result = authorRepository.authorByID(author.id)

            if (result == null) {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = ErrorResponse(error = "NOT_FOUND", message = "Author with id ${author.id} not found")
                )
            } else {
                call.respond(result)
            }
        }

        get<Genres> { _ ->
            val genres = genreRepository.allGenres()
            call.respond(genres)
        }

        get<Endowments> { _ ->
            val endowments = endowmentRepository.allEndowments()
            call.respond(endowments)
        }
    }
}
