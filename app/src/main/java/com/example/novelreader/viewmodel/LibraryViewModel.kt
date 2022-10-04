package com.example.novelreader.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novelreader.ApiService
import com.example.novelreader.HtmlConverter
import com.example.novelreader.model.Paragraph
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

class LibraryViewModel : ViewModel() {

    var title by mutableStateOf("")

    var list = mutableStateListOf<Paragraph>()

    init { update() }

    fun update() {
        viewModelScope.launch {

            val apiService = ApiService.getInstance("https://sads07.wordpress.com")
            val response = apiService.getChapter()

            val jsoup: Document = Jsoup.parse(response)

            val t = jsoup.select(".entry-title")
                .first()
                ?.html()
                ?.replace("&nbsp;", " ")

            val content = jsoup.select(".entry-content")
                .first()

            title = t.toString()

            list.clear()
            fillList(content.toString())
        }
    }

    private fun fillList(html: String) {
        val jsoup = Jsoup.parse(html)

        val title = jsoup.select("h2").first()
        if (title != null)
            list.add(
                Paragraph(
                    0,
                    title.html(),
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = 27.sp)) {
                            append(HtmlConverter.paragraphToAnnotatedString(title))
                        }
                    }
                )
            )

        for ((i, p) in jsoup.select("p").withIndex()) {

            if (i == 0) continue

            list.add(
                Paragraph(
                    i,
                    p.html(),
                    HtmlConverter.paragraphToAnnotatedString(p)
                )
            )
        }
    }
}