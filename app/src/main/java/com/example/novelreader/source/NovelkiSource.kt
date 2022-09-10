package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title
import com.example.novelreader.viewmodel.TitlesViewModel

class NovelkiSource: SourceInterface{

    var viewModel: TitlesViewModel? = null

    override val id: Long
        get() = 1

    override val name: String
        get() = "Novelki.pl"

    override suspend fun getTitleDetails(manga: Title): Title {
        TODO("Not yet implemented")
    }

    override suspend fun getChapterList(manga: Title): List<Chapter> {
        TODO("Not yet implemented")
    }
}
