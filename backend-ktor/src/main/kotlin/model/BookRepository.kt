package dev.obnx.model

import dev.obnx.resources.Books
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
interface BookRepository {
    suspend fun allBooks(): List<Book>
    suspend fun findFilteredBooks(filter: Books): List<Book>
    suspend fun bookByID(id: Long): Book?

    companion object {
        const val DEFAULT_LIMIT = 100
    }
}