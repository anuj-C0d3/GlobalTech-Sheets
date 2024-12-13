package com.example.globaltechsheets

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration {
    val MIGRATION_1_2 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Add the new column
            database.execSQL("ALTER TABLE JobsheetTable ADD COLUMN mobile TEXT DEFAULT '' NOT NULL")
        }
    }

}