package com.example.novelreader.database.repository

import com.example.novelreader.HtmlConverter
import com.example.novelreader.database.dao.ParagraphDao
import com.example.novelreader.database.model.Paragraph
import org.jsoup.Jsoup

class ParagraphRepository(private val paragraphDao: ParagraphDao) {

    fun getDesciption(novelId: Long): List<Paragraph> {
        val result = paragraphDao.getByNovelId(novelId)
        result.forEach {
            it.annotatedString = HtmlConverter.paragraphToAnnotatedString(Jsoup.parse(it.html))
        }
        return result
    }

    suspend fun addDescription(novelId: Long, description: List<Paragraph>) {
        for (p in description) {
            p.novelId = novelId
            paragraphDao.insert(p)
        }
    }

    suspend fun updateDescription(novelId: Long, description: List<Paragraph>) {
        for (p in description) {
            p.novelId = novelId
            paragraphDao.update(p)
        }
    }

    fun deleteByNovelId(novelId: Long) {
        paragraphDao.deleteByNovelId(novelId)
    }

    suspend fun add(item: Paragraph): Long {
        val id = paragraphDao.insert(item)
        item.id = id
        return id
    }

    suspend fun update(item: Paragraph) {
        paragraphDao.update(item)
    }

    suspend fun delete(item: Paragraph) {
        paragraphDao.delete(item)
    }
}