package dev.obnx

import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
//        exception<Throwable> { call, cause ->
//            System.err.println("Error: $cause")
//            call.respondText(text = "An internal error occurred.", status = HttpStatusCode.InternalServerError)
//        }
    }

    routing {
        staticResources("/", "static")
    }
}
