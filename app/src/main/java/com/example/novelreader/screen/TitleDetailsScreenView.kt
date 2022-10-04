package com.example.novelreader.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.novelreader.model.Novel

@Composable
fun TitleDetailsScreenView(
    mainNavController: NavController,
    novel: Novel?
) {
    if (novel == null) {
        Text("pusto")
    } else {
        Column {
            // cover
//            Text(text = novel.title)
            LazyColumn {
                items(novel.description){ el->
                    Text(el.annotatedString)
                }
            }
            // TODO
//            LazyColumn {
//                items(novel.chapterList){ el->
//                    Text(el.title)
//                }
//            }
        }
    }
}


