package com.example.novelreader.test

import androidx.lifecycle.LiveData

class NameRepository(private val nameDao: NameDao) {

    val readAllData: LiveData<List<NameItem>> = nameDao.getAll()

    suspend fun addName(nameItem: NameItem){
        nameDao.insert(nameItem)
    }

    suspend fun updateName(nameItem: NameItem) {
        nameDao.update(nameItem)
    }

    suspend fun deleteName(nameItem: NameItem) {
        nameDao.delete(nameItem)
    }
}