package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel

class SadsTranslatesRepository :RepositoryInterface {

    override val id: Int
        get() = 1

    override val name: String
        get() = "Sads Translates"

    override fun getNovelList(): List<Novel> {
        TODO("Not yet implemented")
    }

    override fun getNovelDetails(novel: Novel): Novel {
        TODO("Not yet implemented")
    }

    override fun getChapters(novel: Novel): List<Chapter> {
        TODO("Not yet implemented")
    }
}