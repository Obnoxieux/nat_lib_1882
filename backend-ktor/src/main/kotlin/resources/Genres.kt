package dev.obnx.resources

import io.ktor.resources.Resource

@Resource("/genres")
class Genres {
    @Resource("{id}")
    class Id(val parent: Genres = Genres(), val id: Long)
}