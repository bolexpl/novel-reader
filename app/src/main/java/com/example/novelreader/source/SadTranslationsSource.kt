package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title

class SadTranslationsSource(private val id: Int) : SourceInterface {

    override fun getId(): Int = id

    override fun getName(): String = "Sad Translations"

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
        return emptyList()
    }
}