package com.example.novelreader.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import com.example.novelreader.database.NovelDatabase
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.repository.ChapterRepository
import com.example.novelreader.database.repository.NovelRepository
import com.example.novelreader.database.repository.ParagraphRepository
import com.example.novelreader.source.SourceInterface
import com.example.novelreader.source.SadsTranslatesSource
import com.example.novelreader.utility.ImageUtility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // database
    val localList: LiveData<List<Novel>>

    private val novelRepository: NovelRepository
    private val chapterRepository: ChapterRepository
    private val paragraphRepository: ParagraphRepository

    val sources: MutableMap<Int, SourceInterface> = mutableMapOf()

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    var novel by mutableStateOf<Novel?>(null)

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

    fun refreshNovelDetailsFromWeb(novelUrl: String) {
        val curr = currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                novel = curr.getNovelDetails(novelUrl)
            }
        }
    }

    fun refreshNovelDetailsFromDb(novelUrl: String) {
        val curr = getSourceFromUrl(novelUrl)
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                var n = novelRepository.getByUrl(novelUrl)
                if (n == null) {
                    novel = curr.getNovelDetails(novelUrl)
                    return@launch
                }

                n.description = paragraphRepository
                    .getDesciption(n.id)
                    .toMutableList()

                // HtmlConverter.paragraphToAnnotatedString(el)

                n.chapterList = chapterRepository
                    .getByNovelId(n.id)
                    .toMutableList()

                if (n.description.size == 0 || n.chapterList.size == 0) {
                    n = curr.getNovelDetails(novelUrl, true)

                    paragraphRepository.addDescription(n.id, n.description)

                    n.chapterList.forEach { ch ->
                        ch.novelId = n.id
                        ch.id = chapterRepository.add(ch)
                    }
                }

//                  TODO if cover in database
//                      get cover from database
//                    else
//                      get cover from web
//                      add cover to database

                novel = n
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

    fun addNovelToLibrary(novel: Novel, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val n = novelRepository.getByUrl(novel.url)
            if (n != null) {
                novel.inDatabase = false
                ImageUtility.removeDir(ImageUtility.getTitlePath(novel.title, context))
                paragraphRepository.deleteByNovelId(n.id)
                chapterRepository.deleteByNovelId(n.id)
                novelRepository.delete(n)
            } else {
                novel.inDatabase = true
                val coverName = ImageUtility.saveCover(novel.coverUrl, novel.title, context)
                novel.coverName = coverName
                novelRepository.add(novel)
                paragraphRepository.addDescription(novel.id, novel.description)

                novel.chapterList.forEach { ch ->
                    ch.novelId = novel.id
                    ch.id = chapterRepository.add(ch)
                }
            }

            var index = -1
            for (item in novelList.withIndex()) {
                if (item.value.url == novel.url) {
                    index = item.index
                    break
                }
            }

            if (index > -1) {
                novelList[index] = novel
            }
        }
    }

    private fun getSourceFromUrl(url: String): SourceInterface? {
        for (source in sources.values) {
            if (url.contains(source.baseUrl)) {
                return source
            }
        }

        return null
    }
}
