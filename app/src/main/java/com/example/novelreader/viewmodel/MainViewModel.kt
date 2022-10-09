package com.example.novelreader.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.novelreader.database.NovelDatabase
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.repository.NovelRepository
import com.example.novelreader.source.SourceInterface
import com.example.novelreader.source.SadsTranslatesSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val localList: LiveData<List<Novel>>

    private val repository: NovelRepository

    val sources: MutableMap<Int, SourceInterface> = mutableMapOf()

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    var novel by mutableStateOf<Novel?>(null)

    var chapterList: MutableList<Chapter> = mutableStateListOf()

    var chapter: Chapter? by mutableStateOf(null)

    private var currentSource: SourceInterface? by mutableStateOf(null)

    init {
        val novelDao = NovelDatabase.getInstance(application).novelDao()
        repository = NovelRepository(novelDao = novelDao)
        localList = repository.readAllData

        addRepo(SadsTranslatesSource())
    }

    private fun addRepo(r: SourceInterface) {
        sources[r.id] = r
    }

    fun setCurrentRepo(index: Int) {
        currentSource = sources[index]
    }

    fun updateSourceName() {
        sourceName = ""
        currentSource?.let {
            sourceName = it.name
        }
    }

    fun refreshNovelList(newest: Boolean) {
        novelList = mutableStateListOf()
        val curr = currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                novelList.clear()
                if (newest) {
                    novelList.addAll(curr.getNewNovelList())
                } else {
                    novelList.addAll(curr.getAllNovelList())
                }
                repository.checkInDb(novelList)
            }
        }
    }

    fun refreshNovelDetails(novelUrl: String) {
        chapterList = mutableStateListOf()
        val curr = currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                novel = curr.getNovelDetails(novelUrl)
                chapterList = novel!!.chapterList
            }
        }
    }

    fun refreshChapterContent(chapterUrl: String) {
        chapter = null
        val curr = currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                chapter = curr.getChapterContent(chapterUrl)
            }
        }
    }

    fun addNovelToLibrary(novel: Novel) {
        viewModelScope.launch(Dispatchers.IO) {
            val n = repository.getByUrl(novel.url)
            if (n != null) {
                repository.delete(novel)
            } else {
                repository.add(novel)
            }
        }
    }
}
