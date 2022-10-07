package com.example.novelreader.test

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.novelreader.test.NameViewModel
import com.example.novelreader.test.NameViewModelFactory
import com.example.novelreader.ui.theme.NovelReaderTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val context = LocalContext.current
            val viewModel: NameViewModel = viewModel(
                factory = NameViewModelFactory(context.applicationContext as Application)
            )

            NovelReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NamesList(viewModel)
                }
            }
        }
    }
}

@Composable
fun NamesList(viewModel: NameViewModel) {
    val list = viewModel.readAllData.observeAsState(listOf()).value

    LazyColumn {
        items(list) { el ->
            val name by remember { mutableStateOf(el.text) }

            Text(text = name)
            Divider()
        }
    }
}
