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

class MainViewModel : ViewModel() {

//    fun purchaseIngredient(storageItem: StorageItem) {
//        storageItems = storageItems.map { item ->
//            if(item == storageItem)
//                item.copy(stock = item.stock + 1)
//            else
//                item
//        }
//    }

    var sourceName by mutableStateOf("")

    var novelList: MutableList<Novel> = mutableStateListOf()

    val repos: MutableMap<Int, RepositoryInterface> = mutableMapOf()

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

    fun updateNovelList() {
        currentRepo?.let {
            sourceName = it.name
        }
    }

    fun refreshNovelList() {
        val curr = currentRepo
        curr?.let {
            viewModelScope.launch {
                novelList.addAll(curr.getNovelList())
            }
        }
    }
}