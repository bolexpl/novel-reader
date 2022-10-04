package com.example.novelreader.model

import androidx.compose.runtime.mutableStateListOf

data class Chapter(
    var title: String = "",
    var url: String = "",
    var content: MutableList<Paragraph> = mutableStateListOf()
)