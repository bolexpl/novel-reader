package com.example.novelreader.database.repository

import androidx.lifecycle.LiveData
import com.example.novelreader.database.dao.NovelDao
import com.example.novelreader.database.model.Novel

class NovelRepository(private val novelDao: NovelDao) {

    val readAllData: LiveData<List<Novel>> = novelDao.getAll()

    fun getByUrl(url: String): Novel?{
        val n = novelDao.getByUrl(url)
        n?.inDatabase = true
        return n
    }

    suspend fun add(item: Novel):Long {
        item.inDatabase=true
        return novelDao.insert(item)
    }

    suspend fun update(item: Novel) {
        item.inDatabase=true
        novelDao.update(item)
    }

    suspend fun delete(item: Novel) {
        item.inDatabase=false
        novelDao.delete(item)
    }

    fun checkInDb(list: List<Novel>){
        val dbList = novelDao.getListByUrls(list.map { it.url })

        list.forEach {
            it.inDatabase = dbList.contains(it.url)
        }
    }
}