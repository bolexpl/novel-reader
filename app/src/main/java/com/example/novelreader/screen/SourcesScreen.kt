package com.example.novelreader.screen

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.novelreader.MainNavItem
import com.example.novelreader.R
import com.example.novelreader.source.Sources
import com.example.novelreader.ui.theme.EBookReaderTheme

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SourcesScreenPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            SourcesScreen(navController = NavController(LocalContext.current))
        }
    }
}

@Composable
fun SourcesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center)
    ) {
        Scaffold(topBar = { TopBar() }) {
            LazyColumn {
                items(Sources.list) { source ->
                    SourceItem(source.getName(), onClickAll = {
                        navController.navigate(MainNavItem.AllTitlesScreen)
                    }, onClickLatest = {
                        navController.navigate(MainNavItem.LatestTitlesScreen)
                    })
                }
            }
        }
    }
}

@Composable
private fun TopBar() {
    Surface(
        modifier = Modifier.padding(
            start = 10.dp,
            top = 20.dp,
            end = 10.dp,
            bottom = 20.dp,
        ),
        color = MaterialTheme.colors.background
    ) {
        Text(
            text = "Source list",
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
private fun SourceItem(
    text: String,
    onClickAll: (Context) -> Unit = {},
    onClickLatest: (Context) -> Unit = {}
) {
    val context = LocalContext.current

    Divider(color = MaterialTheme.colors.primaryVariant)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = false, onClick = { onClickAll(context) }),
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            modifier = Modifier.padding(20.dp)
        )
        Button(
            onClick = { onClickLatest(context) },
            modifier = Modifier.padding(20.dp)
        ) {
            Text(stringResource(id = R.string.label_latest))
        }
    }
}
