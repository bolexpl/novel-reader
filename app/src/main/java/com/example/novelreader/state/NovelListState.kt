package com.example.novelreader.state

import com.example.novelreader.model.Novel

data class NovelListState(
    var sourceName: String = "Name",
    var novels: List<Novel> = emptyList(),
)