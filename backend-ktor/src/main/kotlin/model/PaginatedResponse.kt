package dev.obnx.model

import kotlinx.serialization.Serializable

@Serializable
data class PaginatedResponse(
    val items: List<DomainModel>,
    val total: Long,
    val limit: Long,
    val offset: Long,
)
