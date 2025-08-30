package dev.obnx.model

import dev.obnx.db.GenreEntity
import dev.obnx.db.genreEntityToModel
import dev.obnx.db.suspendTransaction

class PostgresGenreRepository : GenreRepository {
    override suspend fun allGenres(): List<Genre> = suspendTransaction {
        GenreEntity.all().map(transform = ::genreEntityToModel)
    }

    override suspend fun genreByID(id: Long): Genre? = suspendTransaction {
        val entity = GenreEntity.findById(id.toInt())
        if (entity != null) genreEntityToModel(entity) else null
    }
}