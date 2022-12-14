package com.example.novelreader.database.model

import androidx.compose.runtime.mutableStateListOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "novel")
data class Novel(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "url")
    var url: String = "",
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "coverUrl")
    var coverUrl: String = "",
    @ColumnInfo(name = "coverName")
    var coverName: String = "",

    @ColumnInfo(name= "source_id")
    var sourceId: Int = 0,
    @ColumnInfo(name= "source_name")
    var sourceName: String = "",

    @Ignore
    var inDatabase: Boolean = true,
    @Ignore
    var description: MutableList<Paragraph> = mutableStateListOf(),
    @Ignore
    var chapterList: MutableList<Chapter> = mutableStateListOf()
)
