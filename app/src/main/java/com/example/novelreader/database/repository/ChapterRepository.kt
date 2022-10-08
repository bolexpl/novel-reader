package com.example.novelreader.database.repository

import com.example.novelreader.database.dao.ChapterDao
import com.example.novelreader.database.model.Chapter

class ChapterRepository(private val chapterDao: ChapterDao) {

    suspend fun add(item: Chapter){
        chapterDao.insert(item)
    }

    suspend fun update(item: Chapter) {
        chapterDao.update(item)
    }

    suspend fun delete(item: Chapter) {
        chapterDao.delete(item)
    }
}