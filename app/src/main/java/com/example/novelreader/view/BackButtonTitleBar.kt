package com.example.novelreader.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BackButtonTitleBar(
    mainNavController: NavController,
    title: String = "",
    height: Dp = 70.dp,
    showBackground: Boolean = false,
    color: Color = MaterialTheme.colors.surface,
) {
    if (showBackground) {
        Surface(color = color, modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(height)
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
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(height)
                .fillMaxWidth()
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
}