package com.example.novelreader.database.repository

import androidx.lifecycle.LiveData
import com.example.novelreader.database.dao.NovelDao
import com.example.novelreader.database.model.Novel

class NovelRepository(private val novelDao: NovelDao) {

    val readAllData: LiveData<List<Novel>> = novelDao.getAll()

    suspend fun add(item: Novel){
        novelDao.insert(item)
    }

    suspend fun update(item: Novel) {
        novelDao.update(item)
    }

    suspend fun delete(item: Novel) {
        novelDao.delete(item)
    }
}