package com.example.novelreader.database.model

import androidx.compose.runtime.mutableStateListOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "chapter")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "number")
    var number: Int,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "url")
    var url: String = "",

    @Ignore
    var content: MutableList<Paragraph> = mutableStateListOf()
)