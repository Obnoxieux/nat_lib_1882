package dev.obnx.model

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class FakeBookRepository : BookRepository {
    private val books = mutableListOf(
        Book(
            1, 1, "The Hobbit", "J.R.R. Tolkien",
            manuscript = true,
            print = true,
            comment = "worn and used",
            editorComment = "incomplete, damaged",
            volume = "1"
        ),
        Book(
            2, 2, "The Lord of the Rings", "J.R.R. Tolkien",
            manuscript = true,
            print = false,
            comment = null,
            editorComment = "incomprehensible",
            volume = "3"
        ),
        Book(
            3, 3, "The Hitchhiker's Guide to the Galaxy", "John Doe",
            manuscript = false,
            print = true,
            comment = null,
            editorComment = null,
            volume = "5"
        ),
        Book(
            4, 4, "The Adventures of Huckleberry Finn", "Mark Twain",
            manuscript = false,
            print = true,
            comment = null,
            editorComment = null,
            volume = "6"
        ),
        Book(
            5, 5, "The Da Vinci Code", "Dan Brown",
            manuscript = false,
            print = true,
            comment = null,
            editorComment = null,
            volume = "2"
        ),
    )

    override suspend fun allBooks(): List<Book> = books

    override suspend fun bookByID(id: Long): Book? = books.find {
        it.id == id
    }
}