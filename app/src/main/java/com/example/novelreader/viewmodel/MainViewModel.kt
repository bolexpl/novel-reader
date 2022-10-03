package com.example.novelreader.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.novelreader.model.Chapter
import com.example.novelreader.model.Novel
import com.example.novelreader.repository.RepositoryInterface
import com.example.novelreader.repository.SadsTranslatesRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val repos: MutableMap<Int, RepositoryInterface> = mutableMapOf()

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    var novel by mutableStateOf<Novel?>(null)

    var chapterList: MutableList<Chapter> = mutableStateListOf()

    private var currentRepo: RepositoryInterface? by mutableStateOf(null)

    init {
        addRepo(SadsTranslatesRepository())
    }

    private fun addRepo(r: RepositoryInterface) {
        repos[r.id] = r
    }

    fun setCurrentRepo(index: Int) {
        currentRepo = repos[index]
    }

    fun updateSourceName() {
        currentRepo?.let {
            sourceName = it.name
        }
    }

    fun refreshNovelList(newest: Boolean) {
        val curr = currentRepo
        curr?.let {
            viewModelScope.launch {
                novelList.clear()
                if(newest){
                    novelList.addAll(curr.getNewNovelList())
                }else{
                    novelList.addAll(curr.getAllNovelList())
                }
            }
        }
    }

    fun refreshNovelDetails(novelUrl: String) {
        val curr = currentRepo
        curr?.let {
            viewModelScope.launch {
                novel = curr.getNovelDetails(novelUrl)
                chapterList = novel!!.chapterList
            }
        }
    }
}