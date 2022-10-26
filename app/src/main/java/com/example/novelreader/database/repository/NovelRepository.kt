package com.example.novelreader.database.repository

import androidx.lifecycle.LiveData
import com.example.novelreader.database.dao.NovelDao
import com.example.novelreader.database.model.Novel

class NovelRepository(private val novelDao: NovelDao) {

    val readAllData: LiveData<List<Novel>> = novelDao.getAll()

    fun getByUrl(url: String): Novel? {
        val n = novelDao.getByUrl(url)
        n?.inDatabase = true
        return n
    }

    suspend fun add(item: Novel): Long {
        item.inDatabase = true
        val id = novelDao.insert(item)
        item.id = id
        return id
    }

    suspend fun update(item: Novel) {
        item.inDatabase = true
        novelDao.update(item)
    }

    suspend fun delete(item: Novel) {
        novelDao.delete(item)
        item.inDatabase = false
    }

    fun checkInDb(list: List<Novel>) {
        val dbList = novelDao.getListByUrls(list.map { it.url })

        list.forEach {
            it.inDatabase = dbList.contains(it.url)
        }
    }
}