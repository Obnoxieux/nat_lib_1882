package dev.obnx.model

import dev.obnx.resources.Authors
import dev.obnx.resources.Books
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
interface BookRepository : BaseRepository {
    suspend fun allBooks(): List<Book>
    suspend fun findFilteredBooks(filter: Books): List<Book>
    suspend fun bookByID(id: Long): Book?
    suspend fun booksByAuthorID(filter: Authors.Id.AuthorBooks): List<Book>
}