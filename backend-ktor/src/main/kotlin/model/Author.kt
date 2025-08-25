package dev.obnx.model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val id: Long,
    val name: String
)
