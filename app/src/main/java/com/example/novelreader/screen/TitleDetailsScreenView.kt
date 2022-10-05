package com.example.novelreader.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.HtmlConverter
import com.example.novelreader.R
import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel
import com.example.novelreader.model.Paragraph
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.view.BackButtonTitleBar
import com.example.novelreader.view.ChapterItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import kotlinx.coroutines.delay
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

@Composable
fun TitleDetailsScreenView(
    mainNavController: NavController,
    novel: Novel?,
    onRefresh: (String) -> Unit = {},
    onFavourite: (String) -> Unit = {},
    onItemClick: () -> Unit = {},
) {
    var descExpanded: Boolean by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(3000)
            refreshing = false
        }
    }

    if (novel == null) {
        Column{
            Row(
                horizontalArrangement = Arrangement.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                CircularProgressIndicator()
            }
            BackButtonTitleBar(mainNavController = mainNavController, height = 50.dp)
        }
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = refreshing),
            onRefresh = {
                refreshing = true
                onRefresh(novel.url)
            },
        ) {
            LazyColumn {
                item {
                    BackButtonTitleBar(mainNavController = mainNavController, height = 50.dp)
                }

                // cover
                item {
                    Row {
                        TitleDetailCover(coverUrl = novel.coverUrl)
                        Text(
                            text = novel.title,
                            fontSize = 30.sp,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }

                // button
                item {
                    TitleDetailsButtons()
                }

                // description
                itemsIndexed(items = novel.description) { i, el ->
                    if (el.annotatedString.isNotEmpty()) {
                        if (descExpanded) {
                            Text(
                                text = el.annotatedString,
                                fontSize = 20.sp,
                                modifier = Modifier.clickable { descExpanded = false }
                            )
                        } else if (i == 0) {
                            Text(
                                text = el.annotatedString.plus(AnnotatedString("... Czytaj więcej")),
                                fontSize = 20.sp,
                                modifier = Modifier.clickable { descExpanded = true }
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.padding(20.dp))
                }

                // chapters label
                item {
                    Text(text = "Rozdziały: ${novel.chapterList.size}", fontSize = 25.sp)
                }

                item {
                    Spacer(modifier = Modifier.padding(20.dp))
                }

                // chapters
                items(novel.chapterList) { el ->
                    ChapterItem(item = el, onItemClick = onItemClick)
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun TitleDetailsButtons() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                /* TODO add to library */
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(40.dp)
            )
        }
        IconButton(
            onClick = {
                /* TODO open in browser */
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Browser",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
private fun TitleDetailCover(coverUrl: String) {
    if (coverUrl == "") {
        Image(
            painter = painterResource(id = R.drawable.novel_no_cover),
            contentDescription = "No cover"
        )
    } else {
        GlideImage(
            imageModel = coverUrl,
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
            ),
            modifier = Modifier.width(130.dp).clickable { /* TODO click to zoom */ },
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
                    contentDescription = "Cover"
                )
            },
        )
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TitleDetailsScreenPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            val content = Jsoup.parse("")

            val list = mutableListOf<Paragraph>()

            for ((i, el) in content.childNodes().withIndex()) {
                if (el is TextNode) {
                    list.add(Paragraph(i, el.text(), AnnotatedString(el.text())))
                } else if (el is Element) {
                    list.add(Paragraph(i, el.text(), HtmlConverter.paragraphToAnnotatedString(el)))
                }
            }

            val chapterList = remember {
                mutableStateListOf(
                    Chapter(title = "Chapter 1 – The Wall of Genius", url = ""),
                    Chapter(title = "Chapter 2 – Father and Son", url = ""),
                    Chapter(title = "Chapter 3 – Exclusive Maid", url = ""),
                    Chapter(title = "Chapter 4 – The Demon King’s Magic", url = ""),
                    Chapter(title = "Chapter 5 – Intermission (Father and Emperor)", url = ""),
                    Chapter(title = "Chapter 10 – The World after the Demon King’s Fall", url = "")
                )
            }

            TitleDetailsScreenView(
                mainNavController = rememberNavController(),
                novel = Novel(
                    id = 0,
                    title = "Forbidden master",
                    url = "https://sads07.wordpress.com/projects/forbidden-master/",
                    coverUrl = "https://sads07.files.wordpress.com/2019/11/818ay2fgbal._ac_sl1500_.jpg?w=720",
                    description = list,
                    chapterList = chapterList
                )
            )
        }
    }
}
