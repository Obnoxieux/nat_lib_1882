package dev.obnx.resources

import io.ktor.resources.Resource

@Resource("/endowments")
class Endowments {
    @Resource("{id}")
    class Id(val parent: Endowments = Endowments(), val id: Long) {
        @Resource("/books")
        class EndowmentBooks(
            val parent: Id,
            val id: Long,
            val limit: Long? = null,
            val offset: Long? = null,
        )
    }
}