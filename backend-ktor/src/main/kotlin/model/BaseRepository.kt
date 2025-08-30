package dev.obnx.model

interface BaseRepository {
    companion object {
        const val DEFAULT_LIMIT = 100
        const val DEFAULT_OFFSET = 0
    }
}