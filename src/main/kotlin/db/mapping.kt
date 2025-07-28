package dev.obnx.db

import dev.obnx.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object BookTable : IntIdTable("book") {
    val title = varchar("title", 2048)
    val number = integer("number")
    val genre = integer("genre")
    val manuscript = bool("manuscript")
    val print = bool("print")
    val endowment = integer("endowment")
    val comment = varchar("comment", 2048).nullable()
    val editorComment = varchar("editor_comment", 2048).nullable()
    val volume = varchar("volume", 50).nullable()
}

class BookDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookDAO>(BookTable)

    var title by BookTable.title
    var number by BookTable.number
    var genre by BookTable.genre
    var manuscript by BookTable.manuscript
    var print by BookTable.print
    var endowment by BookTable.endowment
    var comment by BookTable.comment
    var editorComment by BookTable.editorComment
    var volume by BookTable.volume
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

@OptIn(ExperimentalSerializationApi::class)
fun bookDAOToModel(dao: BookDAO) = Book(
    id = dao.id.value.toLong(),
    number = dao.number.toLong(),
    title = dao.title,
    author = null,
    manuscript = dao.manuscript,
    print = dao.print,
    comment = dao.comment,
    editorComment = dao.editorComment,
    volume = dao.volume
)