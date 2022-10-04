package com.example.novelreader

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

object HtmlConverter {

    fun paragraphToAnnotatedString(element: Element): AnnotatedString {
        return buildAnnotatedString {

            val childNodes = element.childNodes()

            for (child in childNodes) {
                if (child is Element) {

                    var style = SpanStyle()

                    if (child.tagName().equals("em")) {
                        style = SpanStyle(fontStyle = FontStyle.Italic)
                    }
                    if (child.tagName().equals("strong")) {
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    }

                    if (child.tagName().equals("s")) {
                        style = SpanStyle(textDecoration = TextDecoration.LineThrough)
                    }

                    if (child.tagName().equals("a")) {
                        style = SpanStyle(color = Color.Gray)
                    }

                    withStyle(style = style) {
                        append(paragraphToAnnotatedString(child))
                    }

                } else if (child is TextNode) {
                    append(child.text())
                }
            }
        }
    }
}