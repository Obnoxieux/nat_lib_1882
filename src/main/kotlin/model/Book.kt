package dev.obnx.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@ExperimentalSerializationApi
data class Book(
    val id: Long,
    val number: Long,
    val title: String,
    val authors: List<Author>,
    val genre: Genre,
    val endowment: Endowment?,
    val manuscript: Boolean,
    val print: Boolean,
    val comment: String?,
    @SerialName("editor_comment")
    val editorComment: String?,
    val volume: String?
)
