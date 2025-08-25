package dev.obnx.db

import dev.obnx.model.Genre
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GenreTable : IntIdTable("genre") {
    val name = varchar("name", 255)
}

class GenreEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GenreEntity>(GenreTable)

    var name by GenreTable.name
}

fun genreEntityToModel(entity: GenreEntity) = Genre(
    entity.id.value.toLong(),
    entity.name
)