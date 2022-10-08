package com.example.novelreader.database.model

import androidx.compose.runtime.mutableStateListOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "novel")
data class Novel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "url")
    var url: String = "",
    @ColumnInfo(name = "coverUrl")
    var coverUrl: String = "",

    @Ignore
    var fromDatabase: Boolean = false,
    @Ignore
    var description: MutableList<Paragraph> = mutableStateListOf(),
    @Ignore
    var chapterList: MutableList<Chapter> = mutableStateListOf()
)
