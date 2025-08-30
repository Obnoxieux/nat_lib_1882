package dev.obnx.resources

import io.ktor.resources.*

@Resource("/books")
class Books(
    val genre: Long? = null,
    val endowment: Long? = null,
    val author: Long? = null,
    val manuscript: Boolean? = null,
    val print: Boolean? = null,
    val limit: Long? = null,
    val offset: Long? = null,
    val search: String? = null,
) {
    @Resource("{id}")
    class Id(val parent: Books = Books(), val id: Long)
}