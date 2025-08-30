package dev.obnx.model

import dev.obnx.db.EndowmentEntity
import dev.obnx.db.endowmentEntityToModel
import dev.obnx.db.suspendTransaction

class PostgresEndowmentRepository : EndowmentRepository {
    override suspend fun allEndowments(): List<Endowment> = suspendTransaction {
        EndowmentEntity.all().map(transform = ::endowmentEntityToModel)
    }

    override suspend fun endowmentByID(id: Long): Endowment? = suspendTransaction {
        val entity = EndowmentEntity.findById(id.toInt())
        if (entity != null) endowmentEntityToModel(entity) else null
    }
}