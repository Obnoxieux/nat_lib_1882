package dev.obnx.model

import dev.obnx.db.AuthorEntity
import dev.obnx.db.authorEntityToModel
import dev.obnx.db.suspendTransaction

class PostgresAuthorRepository : AuthorRepository {
    override suspend fun allAuthors(): List<Author> = suspendTransaction {
        AuthorEntity.all().map(transform = ::authorEntityToModel)
    }
}