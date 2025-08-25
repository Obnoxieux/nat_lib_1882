package dev.obnx.model

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
interface BookRepository {
    suspend fun allBooks(): List<Book>
    suspend fun bookByID(id: Long): Book?
}