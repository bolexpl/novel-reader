package com.example.novelreader

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

data class Paragraph(
    val number: Int,
    val html: String? = null,
    val element: Element?,
    val annotatedString: AnnotatedString? = null
)

fun paragraphToAnnotatedString(element: Element): AnnotatedString {
    return buildAnnotatedString {
        append(element.text())
    }
}

