package com.example.novelreader.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.novelreader.R
import com.example.novelreader.database.model.Novel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun NovelItem(
    novel: Novel,
    onClick: (String) -> Unit,
    onLongPress: (Novel) -> Unit,
    showFavouriteIcon: Boolean = true
) {
    var inDatabase: Boolean by remember { mutableStateOf(novel.inDatabase) }
    val context = LocalContext.current

    Divider(color = MaterialTheme.colors.primaryVariant)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick(novel.url) },
                    onLongPress = {
                        onLongPress(novel)
                        inDatabase = !inDatabase
                        if (inDatabase) {
                            Toast
                                .makeText(context, "Added", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast
                                .makeText(context, "Removed", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                )
            }
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

        if (inDatabase && showFavouriteIcon) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favourite",
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Text(
            text = novel.title,
            fontSize = 20.sp,
            modifier = Modifier.padding(
                start = if (inDatabase) 5.dp else 20.dp,
                top = 20.dp,
                end = 20.dp,
                bottom = 20.dp,
            ),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}
