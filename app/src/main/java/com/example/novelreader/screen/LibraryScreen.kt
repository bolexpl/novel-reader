package com.example.novelreader.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.novelreader.MainNavItem
import com.example.novelreader.database.model.Novel
import com.example.novelreader.view.NovelItem
import com.example.novelreader.view.TopBar

@Composable
fun LibraryScreen(
    novelList: LiveData<List<Novel>>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(topBar = { TopBar("My library") }) {
            val l = novelList.observeAsState().value

            if (l != null && l.isNotEmpty()) {
                LazyColumn(modifier = Modifier.padding(it)) {
                    items(l) { novel ->
                        NovelItem(
                            novel = novel,
                            onClick = {
                                // TODO
                            },
                            onLongPress = {
                                // TODO
                            },
                            showFavouriteIcon = false
                        )
                    }
                }
            } else {
                Text(
                    text = "Emtpy Library",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}