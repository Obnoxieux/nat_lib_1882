package dev.obnx

import dev.obnx.model.Page
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.jte.JteContent
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.di.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.slf4j.event.*

fun Application.configureRouting() {
    install(StatusPages) {
//        exception<Throwable> { call, cause ->
//            System.err.println("Error: $cause")
//            call.respondText(text = "An internal error occurred.", status = HttpStatusCode.InternalServerError)
//        }
    }
    install(Resources)

    routing {
        get("/") {
            val page = Page(
                title = "Damascus National Library 1882 Catalogue",
                description = "Searchable database and REST API with historical catalogue data"
            )
            call.respond(JteContent("index.kte", mapOf("page" to page)))
        }
    }
}
