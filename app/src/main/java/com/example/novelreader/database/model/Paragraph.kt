package com.example.novelreader.database.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

@Entity
data class Paragraph(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "number")
    val number: Int,

    @ColumnInfo(name = "html")
    val html: String = "",

    @Ignore
    val annotatedString: AnnotatedString = AnnotatedString("")
)


