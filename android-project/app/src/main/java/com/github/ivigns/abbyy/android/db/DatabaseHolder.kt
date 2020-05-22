package com.github.ivigns.abbyy.android.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.Closeable
import java.util.concurrent.locks.ReentrantLock

class DatabaseHolder(context: Context) : Closeable {

    private val appSqliteOpenHelper: AppSqliteOpenHelper = AppSqliteOpenHelper(context)

    companion object {
        private var sqLiteDatabase: SQLiteDatabase? = null
        private var databaseOpenCloseBalance = 0
        private val reentrantLock: ReentrantLock = ReentrantLock()
    }

    fun open(): SQLiteDatabase? {
        return try {
            reentrantLock.lock()
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase = appSqliteOpenHelper.writableDatabase
            }
            ++databaseOpenCloseBalance
            sqLiteDatabase
        } finally {
            reentrantLock.unlock()
        }
    }

    override fun close() {
        try {
            reentrantLock.lock()
            --databaseOpenCloseBalance
            if (databaseOpenCloseBalance == 0) {
                sqLiteDatabase?.close()
                sqLiteDatabase = null
            }
        } finally {
            reentrantLock.unlock()
        }
    }

}
