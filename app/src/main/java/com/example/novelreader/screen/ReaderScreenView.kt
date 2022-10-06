package com.example.novelreader.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.novelreader.model.Chapter
import com.example.novelreader.view.BackButtonTitleBar
import com.example.novelreader.view.ProgressSpinner

@Composable
fun ReaderScreenView(
    mainNavController: NavController,
    chapter: Chapter?
) {
    var showTopBar by remember { mutableStateOf(false) }

    if (chapter == null) {

        Column {
            BackButtonTitleBar(mainNavController = mainNavController, height = 50.dp)
            ProgressSpinner()
        }

    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .wrapContentSize(Alignment.Center)
                .padding(10.dp)
                .clickable { showTopBar = !showTopBar }
        ) {
            item {
                Text(
                    text = chapter.title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Left,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }

            items(items = chapter.content) { el ->
                Text(
                    text = el.annotatedString,
                    color = MaterialTheme.colors.primary,
                    textAlign = TextAlign.Left,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }

        if (showTopBar) {
            BackButtonTitleBar(
                mainNavController = mainNavController,
                height = 55.dp,
                showBackground = true
            )
        }
    }
}