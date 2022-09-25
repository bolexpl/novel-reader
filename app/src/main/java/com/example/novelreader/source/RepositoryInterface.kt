package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel

interface RepositoryInterface {

    val id: Int

    val name: String

    fun getNovelList(): List<Novel>

    fun getNovelDetails(novel: Novel): Novel

    fun getChapters(novel: Novel): List<Chapter>
}