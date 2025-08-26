package dev.obnx.model

interface AuthorRepository {
    suspend fun allAuthors(): List<Author>
    suspend fun authorByID(id: Long): Author?
}