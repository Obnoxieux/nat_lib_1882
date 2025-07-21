package dev.obnx

import gg.jte.TemplateEngine
import gg.jte.resolve.DirectoryCodeResolver
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.jte.*
import java.nio.file.Path

fun Application.configureTemplating() {
    install(Jte) {
        val resolver = DirectoryCodeResolver(Path.of("templates"))
        templateEngine = TemplateEngine.create(resolver, gg.jte.ContentType.Html)
    }
}