package com.itram.plugins

import com.itram.model.Note
import com.itram.repositories.NotesRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/notes") {

            // CREATE

            post {
                try {
                    val note = call.receive<Note>()
                    val savedNote = NotesRepository.save(note)
                    call.respond(HttpStatusCode.Created, savedNote)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest, "Bad JSON data body ${e.message}"
                    )
                }
            }

            // READ
            get {
                call.respond(NotesRepository.getAll())
            }

            get("{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or malformed Id"
                )
                val note = NotesRepository.getById(id.toLong()) ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    "No note with id $id"
                )
                call.respond(note)
            }

            // UPDATE
            put {
                try {
                    val note = call.receive<Note>()
                    if (NotesRepository.update(note)) {
                        call.respond(note)
                    } else {
                        call.respond(
                            HttpStatusCode.NotFound,
                            "No note with id ${note.id}"
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest, "Bad JSON data body ${e.message}"
                    )
                }
            }

            // DELETE

            delete("{id}") {
                val id = call.parameters["id"] ?: return@delete call.respond(
                    HttpStatusCode.BadRequest,
                    "Missing or malformed Id"
                )

                if (NotesRepository.delete(id.toLong())) {
                    call.respond(HttpStatusCode.Accepted)
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        "No note with id $id"
                    )
                }
            }

        }
    }
}
