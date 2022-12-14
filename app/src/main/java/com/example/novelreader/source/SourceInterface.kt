package com.example.novelreader.source

import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.model.Paragraph

interface SourceInterface {

    val id: Int

    val name: String

    val baseUrl: String

    suspend fun getAllNovelList(): List<Novel>

    suspend fun getNewNovelList(): List<Novel>

    suspend fun getNovelDetails(novelUrl: String, inDatabase: Boolean = false): Novel

    suspend fun getChapters(novelUrl: String): List<Chapter>

    suspend fun getCover(novelUrl: String): String

    suspend fun getChapterContent(chapterUrl: String): Chapter

    suspend fun parseChapterContent(html: String): MutableList<Paragraph>
}