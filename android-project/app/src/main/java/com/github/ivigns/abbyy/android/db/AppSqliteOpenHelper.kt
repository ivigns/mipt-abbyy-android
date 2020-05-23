package com.github.ivigns.abbyy.android.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AppSqliteOpenHelper(context: Context?)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        const val DATABASE_NAME = "Notes.db"
        const val VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        NoteContract.createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        NoteContract.createTable(db)
    }

}
