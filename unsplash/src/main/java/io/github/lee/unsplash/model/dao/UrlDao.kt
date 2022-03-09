package io.github.lee.unsplash.model.dao

import androidx.room.Dao
import androidx.room.Query
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.lee.core.dao.BaseDao
import io.github.lee.unsplash.global.di.database.AppDatabase
import io.github.lee.unsplash.model.domain.Urls
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UrlModule {
    @Singleton
    @Provides
    fun provideUrlDao(database: AppDatabase) =
        database.urlDao()
}

@Dao
interface UrlDao : BaseDao<Urls> {
    @Query("SELECT * FROM urls WHERE photoId==:photoId")
    suspend fun queryByPhotoId(photoId: String): Urls?
}