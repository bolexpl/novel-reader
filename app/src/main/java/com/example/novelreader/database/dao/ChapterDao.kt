package com.example.novelreader.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.novelreader.database.model.Chapter

@Dao
interface ChapterDao {
    @Query("select * from chapter")
    fun getAll(): LiveData<List<Chapter>>

    @Query("select * from chapter where url = :url")
    fun getByUrl(url: String): Chapter?

    @Query("select * from chapter where novel_id = :novelId")
    fun getByNovelId(novelId: Long): List<Chapter>

    @Query("select distinct chapter_id from paragraph where chapter_id in (:chapterIds)")
    fun getListByChapterIds(chapterIds: List<Long>): List<Long>

    @Query("delete from chapter where novel_id = :novelId")
    fun deleteByNovelId(novelId: Long)

    @Insert
    suspend fun insert(item: Chapter): Long

    @Update
    suspend fun update(item: Chapter)

    @Delete
    suspend fun delete(item: Chapter)
}