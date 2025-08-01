package dev.obnx.model

interface AuthorRepository {
    suspend fun allAuthors(): List<Author>
}