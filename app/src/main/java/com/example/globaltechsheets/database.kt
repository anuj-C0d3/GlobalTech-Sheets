package com.example.globaltechsheets

import android.content.Context
import androidx.room.BuiltInTypeConverters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [Jobsheetdata::class], version = 3)
@TypeConverters(MyTypeCovert::class)
abstract class database :RoomDatabase(){
    abstract fun ozonDao():OzonDaoClass
    companion object{
        private var Instance:database?=null
        fun getDatabase(context: Context):database{
            if(Instance==null){
                synchronized(this){
                    Instance = Room.databaseBuilder(context,database::class.java,"OzonDb")
                        .addMigrations(
                           Migration.MIGRATION_1_2
                        )
                        .build()
                }
            }
            return Instance!!
        }
    }

}