package com.example.novelreader.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.novelreader.ApiService
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements


class LibraryViewModel : ViewModel() {

    var errorMessage: String by mutableStateOf("")

    fun getBody() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val response = apiService.getResponseBody()

                val html = response.string()

                val doc: Document = Jsoup.parse(html)
//                val title = doc.select("#main > .post > div.entry-content > h2 > a").html()
                val title = doc.select(".entry-title")
                    .first()
                    .html()
//                    .replace("&nbsp;", " ")
                val content = doc.select(".entry-content")
                    .first()
                    .html()
//                    .replace("&nbsp;", " ")

                Log.d("retro", title.toString())
                Log.d("retro", content.toString())


            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}