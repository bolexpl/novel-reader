package com.example.novelreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.screen.*

class MainActivity : ComponentActivity() {

    class StateHolder() {}

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
private fun MainNavigationGraph(mainNavController: NavHostController) {

    NavHost(mainNavController, startDestination = MainNavItem.MainScreen) {
        composable(MainNavItem.MainScreen) {
            MainScreenView(mainNavController = mainNavController)
        }
        composable(MainNavItem.AllTitlesScreen) {
            AllTitlesScreenView(mainNavController = mainNavController)
        }
        composable(MainNavItem.LatestTitlesScreen) {
            LatestTitlesScreenView(mainNavController = mainNavController)
        }
    }
}

