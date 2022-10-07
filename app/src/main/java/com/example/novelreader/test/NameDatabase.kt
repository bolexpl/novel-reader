package com.example.novelreader.test

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NameItem::class], version = 1, exportSchema = false)
abstract class NameDatabase : RoomDatabase() {
    abstract fun nameDao(): NameDao

    companion object {

        private var INSTANCE: NameDatabase? = null

        fun getInstance(context: Context): NameDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NameDatabase::class.java,
                        "name_database"
                    ).build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}