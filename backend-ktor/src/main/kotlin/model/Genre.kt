package dev.obnx.model
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    val id: Long,
    val name: String
)
