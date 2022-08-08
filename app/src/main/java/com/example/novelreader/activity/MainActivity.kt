package com.example.novelreader.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.border
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.novelreader.BottomNavItem
import com.example.novelreader.screen.LibraryScreen
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.screen.*
import com.example.novelreader.viewmodel.LibraryViewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<LibraryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EBookReaderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreenView()
                    viewModel.getBody()
                }
            }
        }
    }
}


@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    EBookReaderTheme {
        Surface(
            color = MaterialTheme.colors.background
        ) {
            MainScreenView()
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { BottomNavigation(navController = navController) }) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun BottomNavigation(navController: NavController) {
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
                        painterResource(id = item.icon), contentDescription = context.getString(item.title),
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

@Composable
fun NavigationGraph(navController: NavHostController) {
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
            SourcesScreen()
        }
        composable(BottomNavItem.Others.screen_route) {
            OthersScreen()
        }
    }
}
