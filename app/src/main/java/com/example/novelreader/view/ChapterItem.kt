package com.example.novelreader.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novelreader.model.Chapter

@Composable
fun ChapterItem(
    item: Chapter,
    onItemClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Row(
        modifier = Modifier
            .height(70.dp)
            .clickable(onClick = { onItemClick() })
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxHeight()
                .padding(
                    start = 10.dp,
                    end = 0.dp,
                    top = 0.dp,
                    bottom = 0.dp
                )
                .width(screenWidth.minus(70).dp),
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(end = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = {},
                modifier = Modifier.size(40.dp)
//                    .width(35.dp)
            ) {
                Text(text = "p")
            }
        }
    }
}