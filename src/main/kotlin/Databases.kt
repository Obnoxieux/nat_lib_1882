package dev.obnx

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

/**
 * Connects to the Postgres database and throws exception if environment is not set.
 */
fun Application.configureDatabases() {
    val dbUser = environment.config.property("ktor.database.user").getString()
    val dbPassword = environment.config.property("ktor.database.password").getString()
    val dbUrl = environment.config.property("ktor.database.url").getString()

    Database.connect(
        url = dbUrl,
        user = dbUser,
        password = dbPassword,
    )
}
