package com.example.novelreader.state

import com.example.novelreader.model.Novel

data class NovelListState(
    var sourceName: String = "jeden",
    val novels: List<Novel> = emptyList(),
)