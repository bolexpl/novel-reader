package com.example.novelreader.viewmodel

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryViewModel : ViewModel() {

    var title by mutableStateOf("")
    var body by mutableStateOf("")

    var error by mutableStateOf("")

    init {
        update()
    }

    fun update() {
        viewModelScope.launch {

            val apiService = ApiService.getInstance()
            val html = apiService.getContent()

//            val doc: Document = Jsoup.parse(html)
//            val title = doc.select(".entry-title")
//                .first()
//                ?.html()
//            val content = doc.select(".entry-content")
//                .first()
//                ?.html()
//
//            Log.d("retro", title.toString())
//            Log.d("retro", content.toString())
        }
    }
}