package com.example.novelreader

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.screen.*
import com.example.novelreader.test.NameViewModel
import com.example.novelreader.test.NameViewModelFactory
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.viewmodel.MainViewModel
import com.example.novelreader.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel: MainViewModel = viewModel(
                factory = MainViewModelFactory(application = application)
            )
            val mainNavController = rememberNavController()

            EBookReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold {
                        MainNavigationGraph(
                            mainNavController = mainNavController,
                            padding = it,
                            mainViewModel = mainViewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigationGraph(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel,
    padding: PaddingValues
) {
    NavHost(
        mainNavController,
        startDestination = MainNavItem.MainScreen,
        modifier = Modifier.padding(padding)
    ) {
        composable(MainNavItem.MainScreen) {
            MainScreenView(
                mainNavController = mainNavController,
                mainViewModel = mainViewModel
            )
        }
        composable(MainNavItem.AllTitlesScreen) {
            AllTitlesScreenView(
                mainNavController = mainNavController,
                sourceName = mainViewModel.sourceName,
                novelList = mainViewModel.novelList,
                onClick = { url ->
                    mainNavController.navigate(MainNavItem.DetailScreen)
                    mainViewModel.novel = null
                    mainViewModel.refreshNovelDetailsFromWeb(url)
                },
                onLongPress = { n ->
                    mainViewModel.addNovelToLibrary(n)
                }
            )
        }
        composable(MainNavItem.LatestTitlesScreen) {
            LatestTitlesScreenView(
                mainNavController = mainNavController,
                sourceName = mainViewModel.sourceName,
                novelList = mainViewModel.novelList,
                onClick = { url ->
                    mainNavController.navigate(MainNavItem.DetailScreen)
                    mainViewModel.novel = null
                    mainViewModel.refreshNovelDetailsFromWeb(url)
                },
                onLongPress = { n ->
                    mainViewModel.addNovelToLibrary(n)
                }
            )
        }
        composable(MainNavItem.DetailScreen) {
            TitleDetailsScreenView(
                mainNavController = mainNavController,
                novel = mainViewModel.novel,
                onRefresh = { url ->
                    mainViewModel.refreshNovelDetailsFromWeb(url)
                },
                onItemClick = { chapterUrl ->
                    mainViewModel.refreshChapterContent(chapterUrl = chapterUrl)
                }
            )
        }
        composable(MainNavItem.ReaderScreen) {
            ReaderScreenView(
                mainNavController = mainNavController,
                chapter = mainViewModel.chapter
            )
        }
    }
}

