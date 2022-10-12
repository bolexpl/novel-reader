package com.example.novelreader.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.BottomNavItem
import com.example.novelreader.MainNavItem
import com.example.novelreader.viewmodel.MainViewModel

@Composable
fun MainScreenView(
    mainNavController: NavController,
    mainViewModel: MainViewModel
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
        NavigationGraph(
            navController = navController,
            mainNavController = mainNavController,
            padding = it,
            mainViewModel = mainViewModel
        )
    }
}

@Composable
private fun NavigationGraph(
    mainNavController: NavController,
    navController: NavHostController,
    padding: PaddingValues,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Library.screen_route,
        modifier = Modifier.padding(padding)
    ) {
        composable(BottomNavItem.Library.screen_route) {
            LibraryScreen(
                novelList = mainViewModel.localList,
                onClick = { url ->
                    mainViewModel.refreshNovelDetailsFromDb(url)
                    mainNavController.navigate(MainNavItem.DetailScreen)
                },
                onLongPress = {
                    // TODO confirm and delete novel from database
                }
            )
        }
        composable(BottomNavItem.Updates.screen_route) {
            UpdatesScreen()
        }
        composable(BottomNavItem.History.screen_route) {
            HistoryScreen()
        }
        composable(BottomNavItem.Explore.screen_route) {
            SourcesScreen(
                mainNavController,
                mainViewModel.sources,
                onSourceClick = { index, newest ->
                    mainViewModel.setCurrentSource(index)
                    mainViewModel.updateSourceName()
                    mainViewModel.refreshNovelList(newest)
                })
        }
        composable(BottomNavItem.Others.screen_route) {
            OthersScreen()
        }
    }
}

@Composable
private fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Library,
        BottomNavItem.Updates,
        BottomNavItem.History,
        BottomNavItem.Explore,
        BottomNavItem.Others,
    )
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        contentColor = Color.Black,
        modifier = Modifier.border(1.dp, MaterialTheme.colors.primary)
    ) {
        val context = LocalContext.current
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = context.getString(item.title),
                        tint = MaterialTheme.colors.primary
                    )
                },
                label = {
                    Text(
                        text = context.getString(item.title),
                        fontSize = 9.sp
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primary.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
