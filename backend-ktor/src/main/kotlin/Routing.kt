package dev.obnx

import dev.obnx.model.Page
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.jte.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
//        exception<Throwable> { call, cause ->
//            System.err.println("Error: $cause")
//            call.respondText(text = "An internal error occurred.", status = HttpStatusCode.InternalServerError)
//        }
    }

    routing {
        staticResources("/static", "static")

        get("/") {
            val page = Page(
                title = "Ktor API Backend",
                description = "Searchable database and REST API with historical catalogue data - backend"
            )
            call.respond(JteContent("index.kte", mapOf("page" to page)))
        }
    }
}
