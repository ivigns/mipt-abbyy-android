package com.github.ivigns.abbyy.android.db

import android.provider.BaseColumns
import java.util.*

class NotesRepository(private val databaseHolder: DatabaseHolder) {

    companion object {
        private val NOTE_PROJECTION = arrayOf(
            BaseColumns._ID,
            NoteContract.NoteEntry.DATE,
            NoteContract.NoteEntry.TEXT_ID,
            NoteContract.NoteEntry.DRAWABLE_ID
        )
    }

    fun listNotes(): List<Note> {
        val notes = mutableListOf<Note>()
        val db = databaseHolder.open() ?: return notes

        databaseHolder.use {
            val cursor = db.query(
                NoteContract.TABLE_NAME, NOTE_PROJECTION, null, null,
                null, null, "${BaseColumns._ID} ASC"
            )
            cursor.use {
                with(it) {
                    while (moveToNext()) {
                        val id = getLong(getColumnIndex(BaseColumns._ID))
                        val date = Date(getLong(getColumnIndex(NoteContract.NoteEntry.DATE)))
                        val text = getInt(getColumnIndex(NoteContract.NoteEntry.TEXT_ID))
                        val imageId = getInt(getColumnIndex(NoteContract.NoteEntry.DRAWABLE_ID))
                        notes.add(Note(id, date, text, imageId))
                    }
                }
            }
        }

        return notes
    }

    fun getNoteWithId(id: Long): Note? {
        val db = databaseHolder.open() ?: return null

        databaseHolder.use {
            val cursor = db.query(
                NoteContract.TABLE_NAME, NOTE_PROJECTION, "${BaseColumns._ID} = ?",
                arrayOf(id.toString()), null, null, null
            )
            cursor.use {
                with(it) {
                    if (moveToFirst()) {
                        val date = Date(getLong(getColumnIndex(NoteContract.NoteEntry.DATE)))
                        val text = getInt(getColumnIndex(NoteContract.NoteEntry.TEXT_ID))
                        val imageId = getInt(getColumnIndex(NoteContract.NoteEntry.DRAWABLE_ID))
                        return Note(id, date, text, imageId)
                    }
                    return null
                }
            }
        }
    }

}
