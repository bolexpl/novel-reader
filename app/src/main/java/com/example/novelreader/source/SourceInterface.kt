package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title

interface SourceInterface {

    val id: Long

    val name: String

    suspend fun getTitleDetails(manga: Title): Title

    suspend fun getChapterList(manga: Title): List<Chapter>
}