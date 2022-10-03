package com.example.novelreader.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novelreader.R
import com.example.novelreader.model.Novel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun NovelItem(novel: Novel) {
    Divider(color = MaterialTheme.colors.primaryVariant)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {

        GlideImage(
            imageModel = novel.coverUrl,
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
