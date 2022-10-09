package com.example.novelreader.database.repository

import com.example.novelreader.database.dao.ParagraphDao
import com.example.novelreader.database.model.Paragraph

class ParagraphRepository(private val paragraphDao: ParagraphDao) {
    suspend fun add(item: Paragraph){
        paragraphDao.insert(item)
    }

    suspend fun update(item: Paragraph) {
        paragraphDao.update(item)
    }

    suspend fun delete(item: Paragraph) {
        paragraphDao.delete(item)
    }
}