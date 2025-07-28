package dev.obnx.model

import dev.obnx.db.BookDAO
import dev.obnx.db.bookDAOToModel
import dev.obnx.db.suspendTransaction
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class PostgresBookRepository : BookRepository {
    override suspend fun allBooks(): List<Book> = suspendTransaction {
        BookDAO.all().map(::bookDAOToModel)
    }

    override suspend fun bookByID(id: Long): Book? = suspendTransaction {
        val dao = BookDAO.findById(id.toInt())

        if (dao != null) {
            return@suspendTransaction bookDAOToModel(dao)
        } else {
            return@suspendTransaction null
        }
    }
}