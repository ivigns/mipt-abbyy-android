package com.github.ivigns.abbyy.android

import android.app.Application
import com.github.ivigns.abbyy.android.db.DatabaseHolder
import com.github.ivigns.abbyy.android.db.NotesRepository

class App: Application() {
    companion object {
        lateinit var noteRepository: NotesRepository private set
        lateinit var databaseHolder: DatabaseHolder private set
    }
    override fun onCreate() {
        super.onCreate()
        databaseHolder = DatabaseHolder(this)
        noteRepository = NotesRepository(databaseHolder)
    }
}
