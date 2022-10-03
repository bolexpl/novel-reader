package com.example.novelreader.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(title: String) {
    Surface(
        modifier = Modifier.padding(
            start = 10.dp,
            top = 30.dp,
            end = 10.dp,
            bottom = 30.dp,
        ),
        color = MaterialTheme.colors.background
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5
        )
    }
}
