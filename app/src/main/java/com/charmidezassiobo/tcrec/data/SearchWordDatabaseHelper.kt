package com.charmidezassiobo.tcrec.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SearchWordDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        private const val DATABASE_NAME = "searchword.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "SearchWord"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_NAME TEXT)"
        db!!.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db!!.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun saveSearchWord(searchWord: String): Long {
        val values = ContentValues().apply {
            put(COLUMN_NAME, searchWord)
        }
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAllSearchWords(): List<String> {
        val searchWords = mutableListOf<String>()
        val query = "SELECT $COLUMN_NAME FROM $TABLE_NAME"
        val cursor = readableDatabase.rawQuery(query, null)
        cursor?.use {
            val columnIndex = it.getColumnIndex(COLUMN_NAME)
            while (cursor.moveToNext()) {
                val query = cursor.getString(columnIndex)
                searchWords.add(query)
            }
        }
        return searchWords
    }

    //Function de suppression de tout le contenu de la table
}