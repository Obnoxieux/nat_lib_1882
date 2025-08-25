package dev.obnx.model

import dev.obnx.db.GenreEntity
import dev.obnx.db.genreEntityToModel
import dev.obnx.db.suspendTransaction

class PostgresGenreRepository : GenreRepository {
    override suspend fun allGenres(): List<Genre> = suspendTransaction {
        GenreEntity.Companion.all().map(transform = ::genreEntityToModel)
    }
}