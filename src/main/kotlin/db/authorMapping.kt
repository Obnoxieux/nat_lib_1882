package dev.obnx.db

import dev.obnx.model.Author
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object AuthorTable : IntIdTable(name = "author") {
    val name = varchar(name = "name", length = 255)
}

class AuthorEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AuthorEntity>(AuthorTable)

    var name by AuthorTable.name
}

fun authorEntityToModel(entity: AuthorEntity) = Author(
    id = entity.id.value.toLong(),
    name = entity.name
)