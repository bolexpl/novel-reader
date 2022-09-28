package com.example.novelreader.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.navigation.NavController
import com.example.novelreader.state.NovelListState
import com.example.novelreader.view.BackButtonTitleBar

@Composable
fun AllTitlesScreenView(
    mainNavController: NavController,
    novelListState: NovelListState
) {
    Scaffold(topBar = {
        BackButtonTitleBar(
            mainNavController = mainNavController,
            novelListState.sourceName
        )
    }) {

        // TODO pobranie danych
//        LazyColumn {
//            items(novelListState.novels) { novel ->
//                Text(text = novel.title)
//            }
//        }
    }
}