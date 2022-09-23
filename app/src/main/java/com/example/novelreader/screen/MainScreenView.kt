package com.example.novelreader.screen

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.BottomNavItem
import com.example.novelreader.ui.theme.EBookReaderTheme

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DefaultPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            MainScreenView(NavController(LocalContext.current))
        }
    }
}

@Composable
fun MainScreenView(mainNavController: NavController) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
//        Column{
//            Text(state.name)
//            Button(onClick = {
//                state.name = "dwa"
//            }) {
//                Text("zmień")
//            }
//        }

        NavigationGraph(navController = navController, mainNavController = mainNavController)
    }
}

@Composable
private fun NavigationGraph(
    mainNavController: NavController,
    navController: NavHostController,

) {
    NavHost(navController, startDestination = BottomNavItem.Library.screen_route) {
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
            SourcesScreen(mainNavController)
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
