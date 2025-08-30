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
            call.respond(
                PaginatedResponse(
                    items = books,
                    total = books.size.toLong(),
                    limit = book.limit ?: BaseRepository.DEFAULT_LIMIT.toLong(),
                    offset = book.offset ?: BaseRepository.DEFAULT_OFFSET.toLong(),
                )
            )
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

        get<Authors.Id.AuthorBooks> { query ->
            val books = bookRepository.booksByAuthorID(query)
            call.respond(
                PaginatedResponse(
                    items = books,
                    total = books.size.toLong(),
                    limit = query.limit ?: BaseRepository.DEFAULT_LIMIT.toLong(),
                    offset = query.offset ?: BaseRepository.DEFAULT_OFFSET.toLong()
                )
            )
        }

        get<Authors> { author ->
            val authors = authorRepository.allAuthors()
            call.respond(
                PaginatedResponse(
                    items = authors,
                    total = authors.size.toLong(),
                    limit = author.limit ?: BaseRepository.DEFAULT_LIMIT.toLong(),
                    offset = author.offset ?: BaseRepository.DEFAULT_OFFSET.toLong()
                )
            )
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

        // TODO: endpoints by ID and booksBy
    }
}
