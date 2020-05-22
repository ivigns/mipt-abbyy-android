package com.github.ivigns.abbyy.android.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.github.ivigns.abbyy.android.R
import java.util.*

object NoteContract {

    const val TABLE_NAME = "notes"

    object NoteEntry: BaseColumns {
        const val DATE = "date"
        const val TEXT_ID = "text_id"
        const val DRAWABLE_ID = "drawable_id"
    }

    private val NOTE_LIST: List<Note> = listOf(
        Note(1, Date(1581465600000), R.string.note_text_1, R.drawable.cat1),
        Note(2, Date(1583020800000), R.string.note_text_2, R.drawable.cat2),
        Note(3, Date(1587772800000), R.string.note_text_3, R.drawable.cat3)
    )

    fun createTable(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NoteEntry.DATE} INTEGER NOT NULL," +
                    "${NoteEntry.TEXT_ID} INTEGER NOT NULL," +
                    "${NoteEntry.DRAWABLE_ID} INTEGER NOT NULL)"
        )
        NOTE_LIST.forEach {
            val values = ContentValues().apply {
                put(NoteEntry.DATE, it.date.time)
                put(NoteEntry.TEXT_ID, it.textId)
                put(NoteEntry.DRAWABLE_ID, it.drawableId)
            }
            db.insert(TABLE_NAME, null, values)
        }
    }

}
