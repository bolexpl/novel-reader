package com.example.novelreader.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novelreader.model.Chapter

@Composable
fun ReaderScreenView(chapter: Chapter?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
            .padding(10.dp)
    ) {
        // TODO
//        item {
//            Text(
//                text = viewModel.title,
//                fontWeight = FontWeight.Bold,
//                color = MaterialTheme.colors.primary,
//                textAlign = TextAlign.Left,
//                fontSize = 30.sp,
//                modifier = Modifier.padding(vertical = 10.dp)
//            )
//        }
//
//        items(viewModel.list) { el ->
//            if (el.annotatedString != null)
//                Text(
//                    text = el.annotatedString,
//                    color = MaterialTheme.colors.primary,
//                    textAlign = TextAlign.Left,
//                    fontSize = 20.sp,
//                    modifier = Modifier.padding(vertical = 10.dp)
//                )
//        }
    }
}