package com.example.novelreader.source

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Title

interface SourceInterface {

    fun getId(): Int

    fun getName(): String

    fun getLatestTitles(): List<String>

    fun getAllTitles(): List<String>

    fun getChapters(title: Title): List<Chapter>
}