package com.example.novelreader.database.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

@Entity(tableName = "paragraph")
data class Paragraph(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "number")
    var number: Int = 0,

    @ColumnInfo(name = "html")
    var html: String = "",

    @Ignore
    var annotatedString: AnnotatedString = AnnotatedString("")
)


