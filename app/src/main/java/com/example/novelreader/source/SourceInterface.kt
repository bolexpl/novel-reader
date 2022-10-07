package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel

interface SourceInterface {

    val id: Int

    val name: String

    suspend fun getAllNovelList(): List<Novel>

    suspend fun getNewNovelList(): List<Novel>

    suspend fun getNovelDetails(novelUrl: String): Novel

    suspend fun getChapters(novelUrl: String): List<Chapter>

    suspend fun getCover(novelUrl: String): String

    suspend fun getChapterContent(chapterUrl: String): Chapter
}