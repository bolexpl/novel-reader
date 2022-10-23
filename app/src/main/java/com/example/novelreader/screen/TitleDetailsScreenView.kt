package com.example.novelreader.screen

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.HtmlConverter
import com.example.novelreader.MainNavItem
import com.example.novelreader.R
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.model.Paragraph
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.view.BackButtonTitleBar
import com.example.novelreader.view.ChapterItem
import com.example.novelreader.view.ProgressSpinner
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
    onDownload: (String) -> Unit = {},
    onItemClick: (String) -> Unit = {},
    onAddToLibrary: (Novel) -> Unit
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
        Column {
            BackButtonTitleBar(mainNavController = mainNavController, height = 50.dp)
            ProgressSpinner()
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
                    TitleDetailsButtons(novel = novel, onAddToLibrary = onAddToLibrary)
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
                    ChapterItem(item = el, onItemClick = {
                        onItemClick(el.url)
                        mainNavController.navigate(MainNavItem.ReaderScreen)
                    }, onDownload = {
                        onDownload(el.url)
                    })
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun TitleDetailsButtons(
    novel: Novel,
    onAddToLibrary: (Novel) -> Unit
) {
    var added: Boolean by remember { mutableStateOf(novel.inDatabase) }
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(novel.url))

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                onAddToLibrary(novel)
                added = !added
            },
            modifier = Modifier.padding(20.dp)
        ) {
            if (added) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(40.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        IconButton(
            onClick = {
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Favorite",
                modifier = Modifier.size(40.dp)
            )
        }

        IconButton(
            onClick = {
                startActivity(context, intent, null)
            },
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_internet),
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
            modifier = Modifier
                .width(130.dp)
                .clickable { /* TODO click to zoom */ },
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
                    list.add(
                        Paragraph(
                            orderNo = i,
                            html = el.text(),
                            annotatedString = AnnotatedString(el.text())
                        )
                    )
                } else if (el is Element) {
                    list.add(
                        Paragraph(
                            orderNo = i,
                            html = el.text(),
                            annotatedString = HtmlConverter.paragraphToAnnotatedString(el)
                        )
                    )
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
                    title = "Forbidden master",
                    url = "https://sads07.wordpress.com/projects/forbidden-master/",
                    coverUrl = "https://sads07.files.wordpress.com/2019/11/818ay2fgbal._ac_sl1500_.jpg?w=720",
                    description = list,
                    chapterList = chapterList
                ),
                onAddToLibrary = {}
            )
        }
    }
}
