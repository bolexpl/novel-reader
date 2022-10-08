package com.example.novelreader.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.source.SourceInterface
import com.example.novelreader.source.SadsTranslatesSource
import com.example.novelreader.test.NameDatabase
import com.example.novelreader.test.NameItem
import com.example.novelreader.test.NameRepository
import com.example.novelreader.test.NameViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<NameItem>>
    private val repository: NameRepository

    val sources: MutableMap<Int, SourceInterface> = mutableMapOf()

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    var novel by mutableStateOf<Novel?>(null)

    var chapterList: MutableList<Chapter> = mutableStateListOf()

    var chapter: Chapter? by mutableStateOf(null)

    private var currentSource: SourceInterface? by mutableStateOf(null)

    init {
        val nameDao = NameDatabase.getInstance(application).nameDao()
        repository = NameRepository(nameDao)
        readAllData = repository.readAllData

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
                    val nn = novelList
                    Log.d("myk", nn.toString())
                } else {
                    novelList.addAll(curr.getAllNovelList())
                }
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
}

class MainViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
