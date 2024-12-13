package com.example.globaltechsheets


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface OzonDaoClass {
    @Query("Select * from jobsheettable")
   suspend fun getAll():List<Jobsheetdata>
    @Insert
    suspend fun insert(data:Jobsheetdata)
    @Delete
    suspend fun delete(data:Jobsheetdata)
    @Update
    suspend fun update(data:Jobsheetdata)
    @Query("SELECT EXISTS(SELECT 1 FROM jobsheettable WHERE requestNo = :id)")
    suspend fun isExist(id:Int):Boolean
    @Query("SELECT signature FROM jobsheettable WHERE requestNo = :id")
   suspend fun getImageById(id: Int): ByteArray?
}