package com.github.ivigns.abbyy.android.db

import android.content.ContentValues
import android.provider.BaseColumns
import java.io.File
import java.util.*

class NotesRepository(private val databaseHolder: DatabaseHolder) {

    companion object {
        private val NOTE_PROJECTION = arrayOf(
            BaseColumns._ID,
            NoteContract.NoteEntry.DATE,
            NoteContract.NoteEntry.TEXT,
            NoteContract.NoteEntry.DRAWABLE_PATH
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
                        val text = getString(getColumnIndex(NoteContract.NoteEntry.TEXT))
                        val imagePath = getString(getColumnIndex(NoteContract.NoteEntry.DRAWABLE_PATH))
                        notes.add(Note(id, date, text, imagePath))
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
                        val text = getString(getColumnIndex(NoteContract.NoteEntry.TEXT))
                        val imagePath = getString(getColumnIndex(NoteContract.NoteEntry.DRAWABLE_PATH))
                        return Note(id, date, text, imagePath)
                    }
                    return null
                }
            }
        }
    }

    fun insertNote(imageText: String, imagePath: String): Long? {
        val db = databaseHolder.open() ?: return null

        databaseHolder.use {
            val values = ContentValues().apply {
                put(NoteContract.NoteEntry.DATE, Date().time)
                put(NoteContract.NoteEntry.TEXT, imageText)
                put(NoteContract.NoteEntry.DRAWABLE_PATH, imagePath)
            }
            val id = db.insert(NoteContract.TABLE_NAME, null, values)
            return if (id == -1L) null else id
        }
    }

    fun deleteNote(id: Long) {
        val note = getNoteWithId(id)
        val db = databaseHolder.open() ?: return

        databaseHolder.use {
            db.delete(NoteContract.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
            note?.let {
                File(note.drawablePath).delete()
            }
        }
    }

}
