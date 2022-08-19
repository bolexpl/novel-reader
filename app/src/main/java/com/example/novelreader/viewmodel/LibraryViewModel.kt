package com.example.novelreader.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novelreader.ApiService
import com.example.novelreader.Paragraph
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class LibraryViewModel : ViewModel() {

    var title by mutableStateOf("")

    var list = mutableStateListOf<Paragraph>()

    init {
        update()
    }

    fun update() {
        viewModelScope.launch {

            val apiService = ApiService.getInstance()
            val response = apiService.getChapter()

            val doc: Document = Jsoup.parse(response)

            val t = doc.select(".entry-title")
                .first()
                ?.html()
                ?.replace("&nbsp;", " ")

            val content = doc.select(".entry-content")
                .first()

            title = t.toString()

//            val b = StringBuilder()
//            for (p in c?.select("p")!!) {
//                val tmp = p.text()
//                tmp.replace("&nbsp;"," ")
//                tmp.replace("<br>","\n")
//                tmp.replace("<br/>","\n")
//                b.append(tmp)
//                b.append(System.getProperty("line.separator"))
//            }

            clean(content.toString())
        }
    }

    private fun clean(html: String) {
        list.clear()

        val jsoup = Jsoup.parse(html)

        for((i, p) in jsoup.select("p").withIndex()){
            list.add(Paragraph(
                i,
                p.text(),
                false,
            ))
        }

//        list.add(Paragraph(1, "aaa", false))
//        list.add(Paragraph(2, "bbb", false))
//        list.add(Paragraph(3, "ccc", false))
    }
}