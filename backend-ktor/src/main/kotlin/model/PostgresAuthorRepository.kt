package dev.obnx.model

import dev.obnx.db.AuthorEntity
import dev.obnx.db.authorEntityToModel
import dev.obnx.db.suspendTransaction

class PostgresAuthorRepository : AuthorRepository {
    override suspend fun allAuthors(): List<Author> = suspendTransaction {
        AuthorEntity.all().map(transform = ::authorEntityToModel)
    }

    override suspend fun authorByID(id: Long): Author? = suspendTransaction {
        val entity = AuthorEntity.findById(id.toInt())

        if (entity != null) {
            return@suspendTransaction authorEntityToModel(entity)
        } else {
            return@suspendTransaction null
        }
    }
}