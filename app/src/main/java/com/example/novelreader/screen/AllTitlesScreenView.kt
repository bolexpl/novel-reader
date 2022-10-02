package com.example.novelreader.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.R
import com.example.novelreader.model.Novel
import com.example.novelreader.state.NovelListState
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.view.BackButtonTitleBar
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun AllTitlesScreenView(
    mainNavController: NavController = rememberNavController(),
    sourceName: String,
    novelList: List<Novel>
) {
    Scaffold(topBar = {
        BackButtonTitleBar(
            mainNavController = mainNavController,
            title = sourceName
        )
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(novelList) { novel ->
                NovelItem(novel = novel)
            }
        }
    }
}

@Composable
fun NovelItem(novel: Novel) {
    Divider(color = MaterialTheme.colors.primaryVariant)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {

        GlideImage(
            imageModel = novel.cover,
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            ),
            modifier = Modifier.width(60.dp),
            previewPlaceholder = R.drawable.novel_no_cover,
            component = rememberImageComponent {
                +ShimmerPlugin(
                    baseColor = Color.DarkGray,
                    highlightColor = Color.LightGray
                )
            },
            failure = {
                Image(
                    painter = painterResource(id = R.drawable.novel_no_cover),
                    contentDescription = ""
                )
            }
        )

        Text(
            text = novel.title,
            fontSize = 20.sp,
            modifier = Modifier.padding(20.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AllTitlesScreenPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            val list = listOf(
                Novel(0, "Forbidden Master", ""),
                Novel(1, "Classroom of the Elite", ""),
                Novel(2, "Kakegurui", "")
            )
            AllTitlesScreenView(
                sourceName = "Nazwa",
                novelList = list
            )
        }
    }
}
