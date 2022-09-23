package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title
import com.example.novelreader.viewmodel.TitlesViewModel

class SadTranslatesSource : SourceInterface {

    var viewModel: TitlesViewModel? = null

    override val id: Long
        get() = 2

    override val name: String
        get() = "Sad Translates"

    override suspend fun getTitleDetails(manga: Title): Title {
        TODO("Not yet implemented")
    }

    override suspend fun getChapterList(manga: Title): List<Chapter> {
        TODO("Not yet implemented")
    }
}