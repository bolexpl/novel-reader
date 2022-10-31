package com.example.novelreader.database.repository

import com.example.novelreader.database.dao.ChapterDao
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel

class ChapterRepository(private val chapterDao: ChapterDao) {

    fun getByNovelId(novelId: Long): List<Chapter> {
        return chapterDao.getByNovelId(novelId)
    }

    fun getByUrl(chapterUrl: String): Chapter? {
        return chapterDao.getByUrl(chapterUrl)
    }

    fun deleteByNovelId(novelId: Long) {
        chapterDao.deleteByNovelId(novelId)
    }

    suspend fun add(item: Chapter): Long {
        val id = chapterDao.insert(item)
        item.inDatabase = true
        item.id = id
        return id
    }

    suspend fun update(item: Chapter) {
        chapterDao.update(item)
    }

    suspend fun delete(item: Chapter) {
        chapterDao.delete(item)
        item.inDatabase = false
    }

    fun checkInDb(list: List<Chapter>) {
        val dbList = chapterDao.getListByChapterIds(list.map { it.id })

        list.forEach {
            it.inDatabase = dbList.contains(it.id)
        }
    }
}