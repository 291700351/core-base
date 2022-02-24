package io.github.lee.tmdb.global.di.database

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RoomDatabase

@Keep
@Entity
data class Test(@PrimaryKey val id: Long)

@Keep
@Database(
    entities = [Test::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    //    abstract fun testDao(): TestDao
}