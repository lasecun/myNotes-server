package com.itram.repositories

import com.itram.model.Note

object NotesRepository {

    private val list = mutableListOf<Note>()
    private var currentId = 1L

    fun getAll(): List<Note> = list

    fun save(note: Note): Note =
        note.copy(id = currentId++)
            .also(list::add)


    fun getById(id: Long): Note? = list.find { it.id == id }

    fun update(note: Note): Boolean =
        list.indexOfFirst { it.id == note.id }
            .takeIf { it > 0 }
            ?.also { list[it] = note }
            .let { it != null }


    fun delete(id: Long): Boolean =
        list.indexOfFirst { it.id == id }
            .takeIf { it > 0 }
            ?.also(list::removeAt)
            .let { it != null }
}