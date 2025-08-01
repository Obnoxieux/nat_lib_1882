package dev.obnx.model

import dev.obnx.db.BookEntity
import dev.obnx.db.bookEntityToModel
import dev.obnx.db.suspendTransaction
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class PostgresBookRepository : BookRepository {
    override suspend fun allBooks(): List<Book> = suspendTransaction {
        BookEntity.all().map(::bookEntityToModel)
    }

    override suspend fun bookByID(id: Long): Book? = suspendTransaction {
        val entity = BookEntity.findById(id.toInt())

        if (entity != null) {
            return@suspendTransaction bookEntityToModel(entity)
        } else {
            return@suspendTransaction null
        }
    }
}