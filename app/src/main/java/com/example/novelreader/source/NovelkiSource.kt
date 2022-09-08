package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title
import com.example.novelreader.viewmodel.NovelkiViewModel

class NovelkiSource(private val id: Int): SourceInterface{

    override fun getId(): Int = id

    override fun getName(): String = "Novelki.pl"

    override fun getLatestTitles(): List<String> {
        return emptyList()
    }

    override fun getAllTitles(): List<String> {
        return emptyList()
    }

    override fun getChapters(title: Title): List<Chapter> {
        return emptyList()
    }

    override fun getSettings(): List<SourceSetting> {
        return listOf(
            SourceSetting(name = "Login", type = String::class.java.name, ""),
            SourceSetting(name = "Password", type = String::class.java.name, "")
        )
    }
}
