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
    var chapterList: MutableList<Chapter> = mutableStateListOf()

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
                val list = if (newest) curr.getNewNovelList() else curr.getAllNovelList()

                novelList.addAll(list)
                novelRepository.checkInDb(novelList)
            }
        }
    }

    fun refreshNovelDetailsFromWeb(novelUrl: String) {
        val curr = currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val n = curr.getNovelDetails(novelUrl)
                novel = n
                chapterList = n.chapterList
                // TODO update db
            }
        }
    }

    fun refreshNovelDetailsFromDb(novelUrl: String) {
        novel = null
        val curr = getSourceFromUrl(novelUrl)
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val n = novelRepository.getByUrl(novelUrl)
                if (n == null) {
                    novel = curr.getNovelDetails(novelUrl)
                    return@launch
                }

                n.description = paragraphRepository
                    .getDesciption(n.id)
                    .toMutableList()

                n.chapterList = chapterRepository
                    .getByNovelId(n.id)
                    .toMutableList()

                if (n.description.size == 0 || n.chapterList.size == 0) {
                    val n2 = curr.getNovelDetails(novelUrl, true)

                    n.description = n2.description
                    n.chapterList = n2.chapterList

                    novelRepository.update(n)

                    paragraphRepository.addDescription(n.id, n.description)

                    n.chapterList.forEach { ch ->
                        ch.novelId = n.id
                        ch.id = chapterRepository.add(ch)
                    }
                }

                chapterRepository.checkInDb(n.chapterList)

                novel = n
                chapterList = n.chapterList
            }
        }
    }

    fun refreshChapterContentFromDb(chapterUrl: String) {
        chapter = null
        val curr = currentSource
        curr?.let {
            viewModelScope.launch(Dispatchers.IO) {
                // TODO load from db
                chapter = curr.getChapterContent(chapterUrl)
            }
        }
    }

    fun addChapterContentToLibrary(chapter: Chapter, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            chapter.inDatabase = true
            val n = novelRepository.getById(chapter.novelId) ?: return@launch

            val curr = sources[n.sourceId]
            curr?.let {
                val newChapter = curr.getChapterContent(chapter.url)

                newChapter.content.forEach {
                    it.chapterId = chapter.id
                    it.novelId = chapter.novelId
                    paragraphRepository.add(it)
                }
            }

            // TODO update chapterList
            var index = -1
            for (item in chapterList.withIndex()) {
                if (item.value.url == chapter.url) {
                    index = item.index
                    break
                }
            }

            if (index > -1) {
                chapterList[index] = chapter.copy()
            }
        }
    }

    fun removeChapterContentFromLibrary(chapter: Chapter, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            chapter.inDatabase = false
            paragraphRepository.deleteByChapterId(chapter.id)

            var index = -1
            for (item in chapterList.withIndex()) {
                if (item.value.url == chapter.url) {
                    index = item.index
                    break
                }
            }

            if (index > -1) {
                chapterList[index] = chapter
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
