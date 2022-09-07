package com.example.novelreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.novelreader.screen.ReaderScreen
import com.example.novelreader.ui.theme.EBookReaderTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EBookReaderTheme {
                // A surface container using the 'background' color from the theme
                ReaderScreen()
            }
        }
    }
}