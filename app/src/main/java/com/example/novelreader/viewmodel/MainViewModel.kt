package com.example.novelreader.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.example.novelreader.model.Novel
import com.example.novelreader.source.RepositoryInterface
import com.example.novelreader.source.SadsTranslatesRepository
import com.example.novelreader.state.NovelScreenState
import com.example.novelreader.state.NovelListState
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedState: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    private val _sourceName: MutableLiveData<String> = MutableLiveData()
    private val _novelList: MutableLiveData<List<Novel>> = MutableLiveData()

    val sourceName: LiveData<String> get() = _sourceName
    val novelList: LiveData<List<Novel>> get() = _novelList

    val repos: MutableMap<Int, RepositoryInterface> = mutableMapOf()

    private var currentRepo: RepositoryInterface? by mutableStateOf(null)

    init {
        val r = SadsTranslatesRepository()
        addRepo(r)
    }

    private fun addRepo(r: RepositoryInterface) {
        repos[r.id] = r
    }

    fun setCurrentRepo(index: Int) {
        currentRepo = repos[index]
    }

    fun updateNovelList() {
        currentRepo?.let {
            novelListState = novelListState.copy(sourceName = it.name)
        }
    }

    fun refreshNovelList() {
        val curr = currentRepo

        curr?.let {
            viewModelScope.launch {

                val novels = curr.getNovelList()
                novelListState = novelListState.copy(novels = novels)
            }
        }
    }

    fun refreshNovelCover(n: Novel) {
        val curr = currentRepo

        curr?.let {
            viewModelScope.launch {
                n.cover = curr.getCover(n)
            }
        }
    }
}