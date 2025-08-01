package dev.obnx.db

import dev.obnx.model.Genre
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object GenreTable : IntIdTable("genre") {
    val name = varchar("name", 255)
}

class GenreDAO(id: EntityID<Int>) : IntEntity(
    id,
) {
    companion object : IntEntityClass<GenreDAO>(GenreTable)

    var name by GenreTable.name
}

fun genreDAOToModel(dao: GenreDAO) = Genre(
    dao.id.value.toLong(), 
    dao.name
)