package com.example.novelreader.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.novelreader.paragraphToAnnotatedString
import com.example.novelreader.viewmodel.LibraryViewModel

@Composable
fun LibraryScreen(viewModel: LibraryViewModel = viewModel()) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .wrapContentSize(Alignment.Center),
    ) {
        item {
            Text(
                text = viewModel.title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Left,
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }

        items(viewModel.list) { el ->
//            Text(
//                text = el.text!!,
//                color = MaterialTheme.colors.primary,
//                textAlign = TextAlign.Left,
//                fontSize = 20.sp,
//                modifier = Modifier.padding(vertical = 20.dp)
//            )

            if (el.html != null)
                Text(text = paragraphToAnnotatedString(el.element!!))
        }
    }
}