package com.github.ivigns.abbyy.android

import java.util.*

object NotesRepository {
    private val NOTE_LIST: MutableMap<Long, Note> = hashMapOf(
        1L to Note(1, Date(1581465600000), R.string.note_text_1, R.drawable.cat1),
        2L to Note(2, Date(1583020800000), R.string.note_text_2, R.drawable.cat2),
        3L to Note(3, Date(1587772800000), R.string.note_text_3, R.drawable.cat3)
    )

    fun listNotes(): List<Note> = NOTE_LIST.values.toList()

    fun getNoteWithId(id: Long): Note? = NOTE_LIST[id]
}
