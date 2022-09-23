package com.example.novelreader.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.novelreader.model.Title

class TitlesViewModel: ViewModel() {

    var list = mutableStateListOf<Title>()

    fun fetchAllTitles() {
    }

    fun fetchLatestTitles() {
    }
}
