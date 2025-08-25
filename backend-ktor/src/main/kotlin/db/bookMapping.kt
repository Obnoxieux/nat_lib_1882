package dev.obnx.db

import dev.obnx.model.Book
import kotlinx.serialization.ExperimentalSerializationApi
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object BookTable : IntIdTable(name = "book") {
    val title = varchar(name = "title", length = 2048)
    val number = integer(name = "number")
    val genre = reference(name = "genre", foreign = GenreTable)
    val manuscript = bool(name = "manuscript")
    val print = bool(name = "print")
    val endowment = optReference(name = "endowment", foreign = EndowmentTable)
    val comment = varchar(name = "comment", length = 2048).nullable()
    val editorComment = varchar(name = "editor_comment", 2048).nullable()
    val volume = varchar(name = "volume", length = 50).nullable()
}

class BookEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BookEntity>(BookTable)

    var title by BookTable.title
    var number by BookTable.number
    var author by AuthorEntity via BookAuthorTable
    var genre by GenreEntity referencedOn BookTable.genre
    var manuscript by BookTable.manuscript
    var print by BookTable.print
    var endowment by EndowmentEntity optionalReferencedOn BookTable.endowment
    var comment by BookTable.comment
    var editorComment by BookTable.editorComment
    var volume by BookTable.volume
}

@OptIn(ExperimentalSerializationApi::class)
fun bookEntityToModel(entity: BookEntity) = Book(
    id = entity.id.value.toLong(),
    number = entity.number.toLong(),
    title = entity.title,
    authors = entity.author.toList().map(::authorEntityToModel),
    genre = entity.genre.let(::genreEntityToModel),
    endowment = entity.endowment?.let(::endowmentEntityToModel),
    manuscript = entity.manuscript,
    print = entity.print,
    comment = entity.comment,
    editorComment = entity.editorComment,
    volume = entity.volume
)