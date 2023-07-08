package com.charmidezassiobo.tcrec.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QueryDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "query_database.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "queries"
        private const val COLUMN_ID = "id"
        private const val COLUMN_QUERY = "query"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_QUERY TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun saveQuery(query: String): Long {
        val values = ContentValues()
        values.put(COLUMN_QUERY, query)
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAllQueries(): List<String> {
        val queries = mutableListOf<String>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = readableDatabase.rawQuery(selectQuery, null)
        cursor?.use {
            val columnIndex = it.getColumnIndex(COLUMN_QUERY)
            while (cursor.moveToNext()) {
                val query = cursor.getString(columnIndex)
                queries.add(query)
            }
        }
        return queries
    }

}
