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
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class LibraryViewModel : ViewModel() {

    var title: String by mutableStateOf("")

    fun update() {
        title = "2"
        viewModelScope.launch {

            title = "3"

            /*
            val apiService = ApiService.getInstance()
            val response = apiService.getContent()
                val html = response

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
             */
        }
    }
}