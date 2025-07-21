package dev.obnx.model

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
interface BookRepository {
    fun allBooks(): List<Book>
    fun bookByID(id: Long): Book?
}