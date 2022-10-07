package com.example.novelreader.test

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "name")
data class NameItem(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0L,

    @ColumnInfo(name = "text")
    val text: String
)
