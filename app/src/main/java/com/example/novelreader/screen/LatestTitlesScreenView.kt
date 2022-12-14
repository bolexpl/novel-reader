package com.example.novelreader.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.database.model.Novel
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.view.BackButtonTitleBar
import com.example.novelreader.view.NovelItem
import com.example.novelreader.view.ProgressSpinner

@Composable
fun LatestTitlesScreenView(
    mainNavController: NavController = rememberNavController(),
    sourceName: String,
    novelList: List<Novel>,
    onClick: (String) -> Unit,
    onLongPress: (Novel) -> Unit
) {
    Scaffold(topBar = {
        BackButtonTitleBar(
            mainNavController = mainNavController,
            title = sourceName
        )
    }) { padding ->
        if (novelList.isEmpty()) {
            ProgressSpinner()
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(novelList) { novel ->
                    NovelItem(
                        novel = novel,
                        onClick = {
                            onClick(it)
                        }, onLongPress = {
                            onLongPress(it)
                        })
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LatestTitlesScreenPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            val list = listOf(
                Novel(0,"", "Forbidden Master", ""),
                Novel(1,"", "Classroom of the Elite", ""),
                Novel(2,"", "Kakegurui", "")
            )
            LatestTitlesScreenView(
                sourceName = "Nazwa",
                novelList = list,
                onClick = {},
                onLongPress = {}
            )
        }
    }
}
