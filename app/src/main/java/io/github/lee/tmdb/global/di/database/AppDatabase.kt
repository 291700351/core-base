package io.github.lee.tmdb.global.di.database

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase


@Keep
@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    //    abstract fun testDao(): TestDao
}