package com.example.novelreader.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.BottomNavItem
import com.example.novelreader.source.RepositoryInterface
import com.example.novelreader.source.SadsTranslatesRepository
import com.example.novelreader.state.NovelListState
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.viewmodel.MainViewModel

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            MainScreenView(
                NavController(LocalContext.current),
                mapOf(
                    Pair(1, SadsTranslatesRepository())
                ),
                onSourceClick = {}
            )
        }
    }
}

@Composable
fun MainScreenView(
    mainNavController: NavController,
    repos: Map<Int, RepositoryInterface>,
    onSourceClick: (Int) -> Unit
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
        NavigationGraph(
            navController = navController,
            mainNavController = mainNavController,
            repos = repos,
            padding = it,
            onSourceClick = onSourceClick
        )
    }
}

@Composable
private fun NavigationGraph(
    mainNavController: NavController,
    navController: NavHostController,
    repos: Map<Int, RepositoryInterface>,
    padding: PaddingValues,
    onSourceClick: (Int) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Library.screen_route,
        modifier = Modifier.padding(padding)
    ) {
        composable(BottomNavItem.Library.screen_route) {
            LibraryScreen()
        }
        composable(BottomNavItem.Updates.screen_route) {
            UpdatesScreen()
        }
        composable(BottomNavItem.History.screen_route) {
            HistoryScreen()
        }
        composable(BottomNavItem.Explore.screen_route) {
            SourcesScreen(mainNavController, repos, onSourceClick)
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
