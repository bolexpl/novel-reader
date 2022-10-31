package com.example.novelreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.novelreader.database.model.Paragraph

@Dao
interface ParagraphDao {
    @Query("select * from paragraph")
    fun getAll(): LiveData<List<Paragraph>>

    @Query("select * from paragraph where novel_id = :novelId order by order_no asc")
    fun getByNovelId(novelId: Long): List<Paragraph>

    @Query("delete from paragraph where novel_id = :novelId")
    fun deleteByNovelId(novelId: Long)

    @Query("delete from paragraph where chapter_id = :chapterId")
    fun deleteByChapterId(chapterId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Paragraph): Long

    @Update
    suspend fun update(item: Paragraph)

    @Delete
    suspend fun delete(item: Paragraph)
}