package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title

class NovelkiSource(private val id: Int): SourceInterface{

    override fun getId(): Int {
        return id
    }

    override fun getName(): String {
        return "Novelki.pl"
    }

    override fun getLatestTitles(): List<String> {
        return emptyList()
    }

    override fun getAllTitles(): List<String> {
        return emptyList()
    }

    override fun getChapters(title: Title): List<Chapter> {
        return emptyList()
    }
}
