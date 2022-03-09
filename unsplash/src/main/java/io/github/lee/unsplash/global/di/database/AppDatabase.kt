package io.github.lee.unsplash.global.di.database

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.lee.unsplash.model.dao.PhotoDao
import io.github.lee.unsplash.model.dao.UrlDao
import io.github.lee.unsplash.model.domain.PhotoBean
import io.github.lee.unsplash.model.domain.Urls


@Keep
@Database(
    entities = [PhotoBean::class, Urls::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun urlDao(): UrlDao
}