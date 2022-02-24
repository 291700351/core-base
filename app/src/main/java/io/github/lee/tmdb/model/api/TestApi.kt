package io.github.lee.tmdb.model.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideApi( retrofit: Retrofit): TestApi =
        retrofit.create(TestApi::class.java)

}
interface TestApi {

    @GET("/")
    suspend fun test(
        @Query("language") l:String = "zh"
    ): Any

}