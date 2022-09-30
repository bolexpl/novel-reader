package com.example.novelreader.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun AllTitlesScreenView(
    mainNavController: NavController = rememberNavController(),
    novelListState: NovelListState
) {
    Scaffold(topBar = {
        BackButtonTitleBar(
            mainNavController = mainNavController,
            novelListState.sourceName
        )
    }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(novelListState.novels) { novel ->
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

        val image = "https://sads07.files.wordpress.com/2019/11/818ay2fgbal._ac_sl1500_.jpg?w=720"
        GlideImage(
            imageModel = image,
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            ),
            modifier = Modifier.width(60.dp),
            previewPlaceholder = R.drawable.novel_no_cover
        )

        GlideImage(
            previewPlaceholder = R.drawable.novel_no_cover,
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            ),
            modifier = Modifier.width(60.dp),
            imageModel = ""
        )

//        Image(
//            painter = painterResource(id = R.drawable.novel_no_cover),
//            contentDescription = stringResource(R.string.cover_desc),
//            modifier = Modifier.width(50.dp)
//        )

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
fun AllTitlesScreenPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            val state = NovelListState()
            state.novels = listOf(
                Novel(0, "Forbidden Master", ""),
                Novel(1, "Classroom of the Elite", ""),
                Novel(2, "Kakegurui", "")
            )
            AllTitlesScreenView(
                novelListState = state
            )
        }
    }
}
