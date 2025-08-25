package dev.obnx.model

interface GenreRepository {
    suspend fun allGenres(): List<Genre>
}