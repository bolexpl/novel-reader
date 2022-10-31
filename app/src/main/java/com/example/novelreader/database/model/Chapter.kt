package com.example.novelreader.database.model

import androidx.compose.runtime.mutableStateListOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "chapter")
data class Chapter(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo(name = "order_no")
    var orderNo: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "url")
    var url: String = "",

    @ColumnInfo(name = "novel_id")
    var novelId: Long = 0,

    @Ignore
    var content: MutableList<Paragraph> = mutableStateListOf(),
    @Ignore
    var inDatabase: Boolean = false,
    @Ignore
    var inProgress: Boolean = false,
)