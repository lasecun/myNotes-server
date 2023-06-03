package com.itram.plugins

import com.itram.repositories.NotesRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/notes") {
            call.respond(NotesRepository.getAll())
        }
    }
}
