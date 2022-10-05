package com.example.novelreader.view

import android.util.DisplayMetrics
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
//    val screenHeight = configuration.screenHeightDp

    Row(
        modifier = Modifier
            .padding(
                start = 10.dp,
                end = 10.dp,
                top = 5.dp,
                bottom = 5.dp
            )
            .height(50.dp)
            .clickable(onClick = { onItemClick() })
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .fillMaxWidth()
                .width(screenWidth.minus(70).dp),
        ) {
            Text(
                text = item.title,
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
//                modifier = Modifier.fillMaxHeight(),
//                textAlign = TextAlign.Center
            )
        }
        Button(onClick = {

        }, modifier = Modifier.width(35.dp)) {
            Text(text = "p")
        }
    }
}