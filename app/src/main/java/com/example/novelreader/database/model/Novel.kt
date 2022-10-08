package com.example.novelreader.database.model

import androidx.compose.runtime.mutableStateListOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "novel")
data class Novel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "url")
    var url: String = "",
    @ColumnInfo(name = "coverUrl")
    var coverUrl: String = "",
    @ColumnInfo(name = "cover_filename")
    var coverFileName: String? = null,

    @Ignore
    var inDatabase: Boolean = false,
    @Ignore
    var description: MutableList<Paragraph> = mutableStateListOf(),
    @Ignore
    var chapterList: MutableList<Chapter> = mutableStateListOf()
)
