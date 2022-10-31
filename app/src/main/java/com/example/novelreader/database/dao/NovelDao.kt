package com.example.novelreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.novelreader.database.model.Novel

@Dao
interface NovelDao {
    @Query("SELECT * from novel")
    fun getAll(): LiveData<List<Novel>>

    @Query("select * from novel where id = :novelId")
    fun getById(novelId: Long): Novel?

    @Query("SELECT * FROM novel where url = :url")
    fun getByUrl(url: String): Novel?

    @Query("select url from novel where url in (:urls)")
    fun getListByUrls(urls: List<String>): List<String>

    @Insert
    suspend fun insert(item: Novel): Long

    @Update
    suspend fun update(item: Novel)

    @Delete
    suspend fun delete(item: Novel)
}