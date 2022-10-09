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
import com.example.novelreader.database.repository.ChapterRepository
import com.example.novelreader.database.repository.NovelRepository
import com.example.novelreader.database.repository.ParagraphRepository
import com.example.novelreader.source.SourceInterface
import com.example.novelreader.source.SadsTranslatesSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val localList: LiveData<List<Novel>>

    private val novelRepository: NovelRepository
    private val chapterRepository: ChapterRepository
    private val paragraphRepository: ParagraphRepository

    val sources: MutableMap<Int, SourceInterface> = mutableMapOf()

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    var novel by mutableStateOf<Novel?>(null)

    var chapterList: MutableList<Chapter> = mutableStateListOf()

    var chapter: Chapter? by mutableStateOf(null)

    private var currentSource: SourceInterface? by mutableStateOf(null)

    init {
        val novelDao = NovelDatabase.getInstance(application).novelDao()
        val chapterDao = NovelDatabase.getInstance(application).chapterDao()
        val paragraphDao = NovelDatabase.getInstance(application).paragraphDao()

        novelRepository = NovelRepository(novelDao = novelDao)
        chapterRepository = ChapterRepository(chapterDao = chapterDao)
        paragraphRepository = ParagraphRepository(paragraphDao = paragraphDao)

        localList = novelRepository.readAllData

        addSource(SadsTranslatesSource())
    }

    private fun addSource(r: SourceInterface) {
        sources[r.id] = r
    }

    fun setCurrentSource(index: Int) {
        currentSource = sources[index]
    }

    fun updateSourceName() {
        sourceName = ""
        currentSource?.let {
            sourceName = it.name
        }
    }

    fun refreshNovelList(newest: Boolean, source: SourceInterface? = null) {
        novelList = mutableStateListOf()
        val curr = source ?: currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                novelList.clear()
                if (newest) {
                    novelList.addAll(curr.getNewNovelList())
                } else {
                    novelList.addAll(curr.getAllNovelList())
                }
                novelRepository.checkInDb(novelList)
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
            val n = novelRepository.getByUrl(novel.url)
            if (n != null) {
                novelRepository.delete(novel)
            } else {
                // TODO add cover
                // TODO add description
                novelRepository.add(novel)
                // TODO add chapters
            }
        }
    }

    fun refreshLibraryNovelDetails(novelUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            novel = novelRepository.getByUrl(novelUrl)
        }
    }
}
