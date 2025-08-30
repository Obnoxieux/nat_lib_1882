package dev.obnx.resources

import io.ktor.resources.*

@Resource("/authors")
class Authors(
    val search: String? = null,
    val limit: Long? = null,
    val offset: Long? = null,
) {
    @Resource("{id}")
    class Id(
        val parent: Authors = Authors(), val id: Long
    ) {
        @Resource("/books")
        class AuthorBooks(
            val parent: Id,
            val id: Long,
            val limit: Long? = null,
            val offset: Long? = null,
        )
    }
}