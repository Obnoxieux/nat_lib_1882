package dev.obnx.model

import dev.obnx.resources.Authors
import dev.obnx.resources.Books
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class FakeBookRepository : BookRepository {
    private val books = mutableListOf(
        Book(
            1, 1, "The Hobbit", listOf(Author(id = 1, name = "J.R.R. Tolkien")),
            genre = Genre(id = 1, name = "Fantasy"),
            endowment = Endowment(id = 1, name = "Endowment"),
            manuscript = true,
            print = true,
            comment = "worn and used",
            editorComment = "incomplete, damaged",
            volume = "1"
        ),
        Book(
            2, 2, "The Lord of the Rings", listOf(Author(id = 2, name = "J.R.R. Tolkien")),
            genre = Genre(id = 2, name = "Fantasy"),
            endowment = Endowment(id = 2, name = "Endowment"),
            manuscript = true,
            print = false,
            comment = null,
            editorComment = "incomprehensible",
            volume = "3"
        ),
        Book(
            3, 3, "The Hitchhiker's Guide to the Galaxy", listOf(Author(id = 3, name = "Douglas Adams")),
            manuscript = false,
            genre = Genre(id = 3, name = "Science Fiction"),
            endowment = Endowment(id = 3, name = "Endowment"),
            print = true,
            comment = null,
            editorComment = null,
            volume = "5"
        ),
        Book(
            4, 4, "The Adventures of Huckleberry Finn", listOf(Author(id = 4, name = "Mark Twain")),
            manuscript = false,
            genre = Genre(id = 4, name = "Science Fiction"),
            endowment = Endowment(id = 4, name = "Endowment"),
            print = true,
            comment = null,
            editorComment = null,
            volume = "6"
        ),
        Book(
            5, 5, "The Da Vinci Code", listOf(Author(id = 5, name = "Dan Brown")),
            manuscript = false,
            genre = Genre(id = 5, name = "Science Fiction"),
            endowment = Endowment(id = 5, name = "Endowment"),
            print = true,
            comment = null,
            editorComment = null,
            volume = "2"
        ),
    )

    override suspend fun allBooks(): List<Book> = books

    override suspend fun findFilteredBooks(filter: Books): List<Book> {
        // TODO: implement filtering
        return books
    }

    override suspend fun bookByID(id: Long): Book? = books.find {
        it.id == id
    }

    override suspend fun booksByAuthorID(filter: Authors.Id.AuthorBooks): List<Book> {
        return books.filter { book -> book.authors.any { it.id == filter.id } }
    }
}