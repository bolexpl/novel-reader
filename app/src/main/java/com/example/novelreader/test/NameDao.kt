package com.example.novelreader.test

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NameDao {
    @Query("SELECT * from name")
    fun getAll(): LiveData<List<NameItem>>

    @Insert
    suspend fun insert(item: NameItem)

    @Update
    suspend fun update(item: NameItem)

    @Delete
    suspend fun delete(item: NameItem)
}