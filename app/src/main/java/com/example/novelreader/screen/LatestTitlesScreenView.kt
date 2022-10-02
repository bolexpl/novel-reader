package com.example.novelreader.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.novelreader.model.Novel
import com.example.novelreader.view.BackButtonTitleBar
import com.example.novelreader.view.NovelItem

@Composable
fun LatestTitlesScreenView(
    mainNavController: NavController,
    sourceName: String,
    novelList: List<Novel>
) {
    Scaffold(topBar = {
        BackButtonTitleBar(
            mainNavController = mainNavController,
            title = "Najnowsze: $sourceName"
        )
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(novelList) { novel ->
                NovelItem(novel = novel)
            }
        }
    }
}


