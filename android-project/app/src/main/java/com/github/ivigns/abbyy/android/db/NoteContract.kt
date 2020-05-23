package com.github.ivigns.abbyy.android.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object NoteContract {

    const val TABLE_NAME = "notes"

    object NoteEntry: BaseColumns {
        const val DATE = "date"
        const val TEXT_ID = "text_id"
        const val DRAWABLE_PATH = "drawable_path"
    }

    fun createTable(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${NoteEntry.DATE} INTEGER NOT NULL," +
                    "${NoteEntry.TEXT_ID} INTEGER NOT NULL," +
                    "${NoteEntry.DRAWABLE_PATH} TEXT NOT NULL)"
        )
    }

}
