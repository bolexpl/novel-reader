package com.example.novelreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.model.Paragraph

@Dao
interface ParagraphDao {
    @Query("SELECT * from paragraph")
    fun getAll(): LiveData<List<Paragraph>>

    @Insert
    suspend fun insert(item: Paragraph)

    @Update
    suspend fun update(item: Paragraph)

    @Delete
    suspend fun delete(item: Paragraph)
}