package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel

interface RepositoryInterface {

    val id: Int

    val name: String

    suspend fun getNovelList(): List<Novel>

    suspend fun getNovelDetails(novel: Novel): Novel

    suspend fun getChapters(novel: Novel): List<Chapter>

    suspend fun getCover(url: String): String
}