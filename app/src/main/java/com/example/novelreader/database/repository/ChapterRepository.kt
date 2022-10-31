package com.example.novelreader.database.repository

import com.example.novelreader.database.dao.ChapterDao
import com.example.novelreader.database.model.Chapter

class ChapterRepository(private val chapterDao: ChapterDao) {

    fun getByNovelId(novelId: Long): List<Chapter> {
        return chapterDao.getByNovelId(novelId)
    }

    fun getByUrl(chapterUrl: String): Chapter? {
        val ch = chapterDao.getByUrl(chapterUrl)
        ch?.inDatabase = true
        return ch
    }

    fun deleteByNovelId(novelId: Long) {
        chapterDao.deleteByNovelId(novelId)
    }

    suspend fun add(item: Chapter): Long {
        val id = chapterDao.insert(item)
        item.id = id
        return id
    }

    suspend fun update(item: Chapter) {
        chapterDao.update(item)
    }

    suspend fun delete(item: Chapter) {
        chapterDao.delete(item)
    }
}