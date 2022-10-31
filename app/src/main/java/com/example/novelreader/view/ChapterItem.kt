package com.example.novelreader.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novelreader.R
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.ui.theme.EBookReaderTheme

@Composable
fun ChapterItem(
    item: Chapter,
    onItemClick: () -> Unit = {},
    onDownload: () -> Unit = {}
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
            IconButton(onClick = onDownload) {
                if (item.inDatabase) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                    )
                } else {
                    if (item.inProgress) {
                        ProgressSpinner(modifier = Modifier.size(30.dp))
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_downward),
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChapterItemPreview() {
    EBookReaderTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.background
        ) {
            ChapterItem(
                item = Chapter(0, 0, "Chapter 1 - titel", "www", 0),
                onItemClick = {},
                onDownload = {}
            )
        }
    }

}
