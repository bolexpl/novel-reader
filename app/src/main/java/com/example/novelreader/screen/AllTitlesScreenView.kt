package com.example.novelreader.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.novelreader.view.BackButtonTitleBar
import com.example.novelreader.viewmodel.TitlesViewModel

@Composable
fun AllTitlesScreenView(mainNavController: NavController) {
    val viewModel: TitlesViewModel = viewModel()

    Scaffold(topBar = { BackButtonTitleBar(mainNavController = mainNavController, "Novelki") }) {
        // TODO pobranie danych
        Text("dół")
    }
}