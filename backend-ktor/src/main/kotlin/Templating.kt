package dev.obnx

import gg.jte.TemplateEngine
import gg.jte.resolve.ResourceCodeResolver
import io.ktor.server.application.*
import io.ktor.server.jte.*
import java.nio.file.Paths

/**
 * Overcomplicated setup due to Ktor class loading in development mode.
 * Reason: https://github.com/casid/jte/issues/241#issuecomment-2473332186
 */
fun Application.configureTemplating() {
    install(Jte) {
        val classLoader = Thread.currentThread().contextClassLoader
        val resolver = ResourceCodeResolver("templates", classLoader)
        templateEngine = TemplateEngine.create(
            resolver,
            Paths.get("kte"),
            gg.jte.ContentType.Html,
            classLoader
        )
    }
}