package com.example.novelreader.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.novelreader.source.RepositoryInterface
import com.example.novelreader.source.SadsTranslatesRepository
import com.example.novelreader.state.NovelScreenState
import com.example.novelreader.state.NovelListState

class MainViewModel(
    private val savedState: SavedStateHandle = SavedStateHandle()
) : ViewModel() {

    val sources: Map<Int, RepositoryInterface> = emptyMap()

    var novelListState: NovelListState by mutableStateOf(NovelListState())
        private set

    var novelScreenState: NovelScreenState by mutableStateOf(NovelScreenState())
        private set

    private val repos: MutableMap<Int, RepositoryInterface> = mutableMapOf()

    init {
        addRepo(SadsTranslatesRepository())
    }

    private fun addRepo(r: RepositoryInterface) {
        repos[r.id] = r
    }

    fun setTwo() {
        novelListState.sourceName = "dwa"
    }
}