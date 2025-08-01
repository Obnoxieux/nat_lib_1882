package dev.obnx.db

import org.jetbrains.exposed.sql.Table

object BookAuthorTable : Table(name = "authors_books") {
    val book = reference(name = "book_id", foreign = BookTable)
    val author = reference(name = "author_id", foreign = AuthorTable)
}