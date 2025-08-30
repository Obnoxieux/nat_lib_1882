package dev.obnx.model

interface EndowmentRepository {
    suspend fun allEndowments(): List<Endowment>
    suspend fun endowmentByID(id: Long): Endowment?
}