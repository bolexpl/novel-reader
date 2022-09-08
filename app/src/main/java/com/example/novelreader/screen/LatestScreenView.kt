package com.example.novelreader.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.novelreader.MainNavItem

@Composable
fun LatestScreenView(mainNavController: NavController) {
    Scaffold(topBar = { TopBar(mainNavController = mainNavController, "Novelki") }) {
        Text("dół")
    }
}

@Composable
private fun TopBar(mainNavController: NavController, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { mainNavController.navigateUp() }) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.h5
        )
    }
}
