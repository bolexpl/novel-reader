package com.example.novelreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel

@Dao
interface NovelDao {
    @Query("SELECT * from novel")
    fun getAll(): LiveData<List<Novel>>

    @Query("SELECT * FROM novel where url = :url")
    fun getByUrl(url: String): LiveData<Novel>

    @Insert
    suspend fun insert(item: Novel)

    @Update
    suspend fun update(item: Novel)

    @Delete
    suspend fun delete(item: Novel)
}