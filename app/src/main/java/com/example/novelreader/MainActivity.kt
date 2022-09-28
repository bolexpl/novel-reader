package com.example.novelreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.novelreader.screen.AllTitlesScreenView
import com.example.novelreader.screen.LatestTitlesScreenView
import com.example.novelreader.screen.MainScreenView
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EBookReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val mainNavController = rememberNavController()
                    Scaffold {
                        MainNavigationGraph(mainNavController = mainNavController)
                    }
                }
            }
        }
    }
}

@Composable
private fun MainNavigationGraph(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel = viewModel()
) {
    val novelListState = mainViewModel.novelListState

    NavHost(mainNavController, startDestination = MainNavItem.MainScreen) {
        composable(MainNavItem.MainScreen) {
            MainScreenView(
                mainNavController = mainNavController,
                repos = mainViewModel.repos,
                onSourceClick = { index ->
                    mainViewModel.setCurrentRepo(index)
                    mainViewModel.updateNovelList()
                    mainViewModel.refreshNovelList()
                }
            )
        }
        composable(MainNavItem.AllTitlesScreen) {

            AllTitlesScreenView(
                mainNavController = mainNavController,
                novelListState = novelListState
            )
        }
        composable(MainNavItem.LatestTitlesScreen) {
            LatestTitlesScreenView(
                mainNavController = mainNavController
            )
        }
    }
}

