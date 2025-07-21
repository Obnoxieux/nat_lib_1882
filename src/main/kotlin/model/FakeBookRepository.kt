package dev.obnx.model

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class FakeBookRepository : BookRepository {
    private val books = mutableListOf(
        Book(
            1, 1, "The Hobbit", "J.R.R. Tolkien",
            manuscript = true,
            print = true,
            comment = null,
            editorComment = null,
            volume = null
        ),
        Book(
            2, 2, "The Lord of the Rings", "J.R.R. Tolkien",
            manuscript = true,
            print = true,
            comment = null,
            editorComment = null,
            volume = null
        ),
        Book(
            3, 3, "The Hitchhiker's Guide to the Galaxy", "John Doe",
            manuscript = true,
            print = true,
            comment = null,
            editorComment = null,
            volume = null
        ),
        Book(
            4, 4, "The Adventures of Huckleberry Finn", "Mark Twain",
            manuscript = true,
            print = true,
            comment = null,
            editorComment = null,
            volume = null
        ),
        Book(
            5, 5, "The Da Vinci Code", "Dan Brown",
            manuscript = true,
            print = true,
            comment = null,
            editorComment = null,
            volume = null
        ),
    )

    override fun allBooks(): List<Book> = books

    override fun bookByID(id: Long): Book? = books.find {
        it.id == id
    }
}