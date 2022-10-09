package com.example.novelreader.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.novelreader.database.dao.ChapterDao
import com.example.novelreader.database.dao.NovelDao
import com.example.novelreader.database.dao.ParagraphDao
import com.example.novelreader.database.model.Chapter
import com.example.novelreader.database.model.Novel
import com.example.novelreader.database.model.Paragraph

@Database(
    entities = [Novel::class, Chapter::class, Paragraph::class],
    version = 1,
    exportSchema = false
)
abstract class NovelDatabase : RoomDatabase() {

    abstract fun novelDao(): NovelDao

    abstract fun chapterDao(): ChapterDao

    abstract fun paragraphDao(): ParagraphDao

    companion object {
        private var INSTANCE: NovelDatabase? = null

        fun getInstance(context: Context): NovelDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NovelDatabase::class.java,
                        "novel_database"
                    ).build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}