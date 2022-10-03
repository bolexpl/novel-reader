package com.example.novelreader.model

import androidx.compose.runtime.mutableStateListOf

data class Novel(
    var id: Int = 0,
    var title: String = "",
    var url: String = "",
    var coverUrl: String = "",
    var description: MutableList<Paragraph> = mutableStateListOf(),
    var chapterList: MutableList<Chapter> = mutableStateListOf()
)
