package com.example.novelreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.novelreader.database.model.Chapter

@Dao
interface ChapterDao {
    @Query("SELECT * from chapter")
    fun getAll(): LiveData<List<Chapter>>

    @Query("SELECT * FROM chapter where url = :url")
    fun getByUrl(url: String): LiveData<Chapter>

    @Insert
    suspend fun insert(item: Chapter)

    @Update
    suspend fun update(item: Chapter)

    @Delete
    suspend fun delete(item: Chapter)
}