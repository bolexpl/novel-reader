package com.example.novelreader.screen

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.novelreader.view.BackButtonTitleBar

@Composable
fun LatestTitlesScreenView(mainNavController: NavController) {
    Scaffold(topBar = { BackButtonTitleBar(mainNavController = mainNavController, "Novelki") }) {
        // TODO pobranie danych
        Text("latest")
    }
}


