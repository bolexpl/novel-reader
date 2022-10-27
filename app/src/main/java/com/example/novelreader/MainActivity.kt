package com.example.novelreader

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.novelreader.screen.*
import com.example.novelreader.ui.theme.EBookReaderTheme
import com.example.novelreader.viewmodel.MainViewModel
import com.example.novelreader.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            readPermissionGranted = permissions[READ_EXTERNAL_STORAGE] ?: readPermissionGranted
            writePermissionGranted = permissions[WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted
        }

        requestPermissions()

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

    private fun requestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            this,
            READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ContextCompat.checkSelfPermission(
            this,
            WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest = mutableListOf<String>()
        if (!writePermissionGranted) {
            permissionsToRequest.add(WRITE_EXTERNAL_STORAGE)
        }
        if (!readPermissionGranted) {
            permissionsToRequest.add(READ_EXTERNAL_STORAGE)
        }
        if (permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}

@Composable
private fun MainNavigationGraph(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel,
    padding: PaddingValues
) {
    val context = LocalContext.current
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
                    mainViewModel.refreshNovelDetailsFromDb(url)
                    // TODO download
                },
                onLongPress = { n ->
                    mainViewModel.addNovelToLibrary(n, context)
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
                    mainViewModel.refreshNovelDetailsFromDb(url)
                },
                onLongPress = { n ->
                    mainViewModel.addNovelToLibrary(n, context)
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
                },
                onAddToLibrary = { n ->
                    mainViewModel.addNovelToLibrary(n, context)
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

