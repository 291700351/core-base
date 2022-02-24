package io.github.lee.tmdb.global.di.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, context.packageName + ".db"
        ).build()
    }

    //    @Singleton
    //    @Provides
    //    fun provideTestDao(database: AppDatabase): TestDao {
    //        return database.testDao()
    //    }

}