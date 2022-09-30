package com.example.novelreader.state

import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel

data class NovelScreenState(
    val novel: Novel? = null,
    val chapters: List<Chapter> = emptyList()
)
