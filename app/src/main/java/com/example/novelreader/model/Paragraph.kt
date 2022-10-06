package com.example.novelreader.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

data class Paragraph(
    val number: Int,
    val html: String? = null,
    val annotatedString: AnnotatedString = AnnotatedString("")
)


