package com.example.novelreader.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel
import com.example.novelreader.source.SourceInterface
import com.example.novelreader.source.SadsTranslatesSource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repos: MutableMap<Int, SourceInterface> = mutableMapOf()

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    var novel by mutableStateOf<Novel?>(null)

    var chapterList: MutableList<Chapter> = mutableStateListOf()

    var chapter: Chapter? by mutableStateOf(null)

    private var currentRepo: SourceInterface? by mutableStateOf(null)

    init {
        addRepo(SadsTranslatesSource())
    }

    private fun addRepo(r: SourceInterface) {
        repos[r.id] = r
    }

    fun setCurrentRepo(index: Int) {
        currentRepo = repos[index]
    }

    fun updateSourceName() {
        sourceName = ""
        currentRepo?.let {
            sourceName = it.name
        }
    }

    fun refreshNovelList(newest: Boolean) {
        novelList = mutableStateListOf()
        val curr = currentRepo
        curr?.let {
            viewModelScope.launch {
                novelList.clear()
                if(newest){
                    novelList.addAll(curr.getNewNovelList())
                    val nn = novelList
                    Log.d("myk", nn.toString())
                }else{
                    novelList.addAll(curr.getAllNovelList())
                }
            }
        }
    }

    fun refreshNovelDetails(novelUrl: String) {
        chapterList = mutableStateListOf()
        val curr = currentRepo
        curr?.let {
            viewModelScope.launch {
                novel = curr.getNovelDetails(novelUrl)
                chapterList = novel!!.chapterList
            }
        }
    }

    fun refreshChapterContent(chapterUrl: String) {
        chapter = null
        val curr = currentRepo
        curr?.let {
            viewModelScope.launch {
                chapter = curr.getChapterContent(chapterUrl)
            }
        }
    }
}