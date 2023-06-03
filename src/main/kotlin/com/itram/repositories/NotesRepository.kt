package com.itram.repositories

import com.itram.model.Note
import com.itram.model.Note.Type.AUDIO
import com.itram.model.Note.Type.TEXT

object NotesRepository {

    fun getAll(): List<Note> {
        val notes = (1..10).map {
            Note(
                title = "Title $it",
                description = "Description $it",
                if (it % 3 == 0) AUDIO else TEXT
            )
        }

        return notes
    }
}