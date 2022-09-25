package com.example.novelreader.screen

import androidx.compose.foundation.layout.Column
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
    novelListState: NovelListState,
    onButtonClick: (String) -> Unit = {}
) {
    Scaffold(topBar = { BackButtonTitleBar(mainNavController = mainNavController, "Novelki") }) {
        // TODO pobranie danych
        // TODO przetestować zmianę napisu

        Column {
            Text(novelListState.sourceName)
            Button(onClick = {
                onButtonClick("trzy")
            }) {
                Text("Zmień na trzy")
            }
        }
    }
}