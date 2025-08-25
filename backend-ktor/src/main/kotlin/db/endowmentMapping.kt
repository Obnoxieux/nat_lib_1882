package dev.obnx.db

import dev.obnx.model.Endowment
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object EndowmentTable : IntIdTable("endowment") {
    val name = varchar("name", 255)
}

class EndowmentEntity(id: EntityID<Int>) : IntEntity (id) {
    companion object : IntEntityClass<EndowmentEntity>(EndowmentTable)

    var name by EndowmentTable.name
}

fun endowmentEntityToModel(entity: EndowmentEntity) = Endowment(
    entity.id.value.toLong(),
    entity.name
)