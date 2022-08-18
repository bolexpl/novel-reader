package com.example.novelreader.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novelreader.ApiService
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class LibraryViewModel : ViewModel() {

    var title by mutableStateOf("")
    var html by mutableStateOf("")
    var body by mutableStateOf("")

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
                ?.replace("&nbsp;"," ")

            val c = doc.select(".entry-content")
                .first()

            val b = StringBuilder()
            for (p in c?.select("p")!!) {
                val tmp = p.text()
                tmp.replace("&nbsp;"," ")
                tmp.replace("<br>","\n")
                tmp.replace("<br/>","\n")
                b.append(tmp)
                b.append(System.getProperty("line.separator"))
            }

            title = t.toString()
            html = c.toString()
            body = b.toString()
        }
    }
}