package dev.obnx.resources

import io.ktor.resources.Resource

@Resource("/authors")
class Authors {
    @Resource("{id}")
    class Id(val parent: Authors = Authors(), val id: Long)
}