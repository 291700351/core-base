package io.github.lee.unsplash.model.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.lee.core.dao.BaseDao
import io.github.lee.unsplash.global.di.database.AppDatabase
import io.github.lee.unsplash.model.domain.PhotoAndUrl
import io.github.lee.unsplash.model.domain.PhotoBean
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {
    @Singleton
    @Provides
    fun providePhotoDao(database: AppDatabase): PhotoDao {
        return database.photoDao()
    }
}

@Dao
interface PhotoDao : BaseDao<PhotoBean> {

    @Query("SELECT * FROM photobean WHERE id==:id")
    suspend fun queryById(id: String): PhotoBean?

    @Transaction
    @Query("SELECT * FROM photobean")
    suspend fun query(): List<PhotoAndUrl>

}