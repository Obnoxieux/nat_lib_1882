package dev.obnx.model

import dev.obnx.db.*
import dev.obnx.resources.Authors
import dev.obnx.resources.Books
import dev.obnx.resources.Endowments
import dev.obnx.resources.Genres
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.or

@OptIn(ExperimentalSerializationApi::class)
class PostgresBookRepository : BookRepository {
    override suspend fun allBooks(): List<Book> = suspendTransaction {
        BookEntity.all().map(::bookEntityToModel)
    }

    override suspend fun findFilteredBooks(filter: Books): List<Book> = suspendTransaction {
        val query = getBookBaseQuery()

        filter.genre?.let {
            query.andWhere { BookTable.genre eq filter.genre.toInt() }
        }

        filter.author?.let {
            query.andWhere { BookAuthorTable.author eq filter.author.toInt() }
        }

        filter.endowment?.let {
            query.andWhere { BookTable.endowment eq filter.endowment.toInt() }
        }

        filter.manuscript?.let {
            query.andWhere { BookTable.manuscript eq filter.manuscript }
        }

        filter.print?.let {
            query.andWhere { BookTable.print eq filter.print }
        }

        filter.search?.let {
            query.andWhere {
                (BookTable.title like "%${filter.search}%") or (AuthorTable.name like "%${filter.search}%" or (BookTable.comment like "%${filter.search}%") or (BookTable.editorComment like "%${filter.search}%"))
            }
        }

        query.limit(count = filter.limit?.toInt() ?: BaseRepository.DEFAULT_LIMIT)
        query.offset(start = filter.offset ?: 0)

        val books = BookEntity.wrapRows(query).map(::bookEntityToModel)
        return@suspendTransaction books
    }

    override suspend fun bookByID(id: Long): Book? = suspendTransaction {
        val entity = BookEntity.findById(id.toInt())

        if (entity != null) {
            return@suspendTransaction bookEntityToModel(entity)
        } else {
            return@suspendTransaction null
        }
    }

    private suspend inline fun <reified T> getBooksByID(id: Int, limit: Int?, offset: Long?): List<Book> =
        suspendTransaction {
            val query = getBookBaseQuery()

            when (T::class) {
                Authors.Id.AuthorBooks::class ->
                    query.andWhere {
                        BookAuthorTable.author eq id
                    }

                Genres.Id.GenreBooks::class ->
                    query.andWhere {
                        BookTable.genre eq id
                    }

                Endowments.Id.EndowmentBooks::class ->
                    query.andWhere {
                        BookTable.endowment eq id
                    }
            }

            query.limit(count = limit ?: BaseRepository.DEFAULT_LIMIT)
            query.offset(start = offset ?: 0)

            val books = BookEntity.wrapRows(query).map(::bookEntityToModel)
            return@suspendTransaction books
        }

    override suspend fun booksByAuthorID(filter: Authors.Id.AuthorBooks): List<Book> {
        return getBooksByID<Authors.Id.AuthorBooks>(
            id = filter.id.toInt(),
            limit = filter.limit?.toInt(),
            offset = filter.offset
        )
    }

    override suspend fun booksByGenreID(filter: Genres.Id.GenreBooks): List<Book> {
        return getBooksByID<Genres.Id.GenreBooks>(
            id = filter.id.toInt(),
            limit = filter.limit?.toInt(),
            offset = filter.offset
        )
    }

    override suspend fun booksByEndowmentID(filter: Endowments.Id.EndowmentBooks): List<Book> {
        return getBooksByID<Endowments.Id.EndowmentBooks>(
            id = filter.id.toInt(),
            limit = filter.limit?.toInt(),
            offset = filter.offset
        )
    }

    private fun getBookBaseQuery(): Query {
        return BookTable.innerJoin(BookAuthorTable).innerJoin(AuthorTable)
            .select(AuthorTable.columns + BookTable.columns)
    }
}