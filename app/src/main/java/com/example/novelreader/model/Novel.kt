package com.example.novelreader.model

data class Novel(
    val id: Int,
    val title: String,
    val url: String,
    var cover: String? = null
)
