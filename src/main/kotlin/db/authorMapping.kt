package dev.obnx.db

import dev.obnx.model.Author
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object AuthorTable : IntIdTable("author") {
    val name = varchar("name", 255)
}

class AuthorDAO(id: EntityID<Int>) : IntEntity(
    id,
) {
    companion object : IntEntityClass<AuthorDAO>(AuthorTable)

    var name by AuthorTable.name
}

fun authorDAOToModel(dao: AuthorDAO) = Author(
    dao.id.value.toLong(),
    dao.name
)