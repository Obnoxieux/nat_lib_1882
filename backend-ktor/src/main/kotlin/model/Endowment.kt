package dev.obnx.model

import kotlinx.serialization.Serializable

@Serializable
data class Endowment(
    val id: Long,
    val name: String
)
