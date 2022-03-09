package io.github.lee.unsplash.model.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.lee.unsplash.model.domain.PhotoBean
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): PhotoApi =
        retrofit.create(PhotoApi::class.java)

}

interface PhotoApi {

    @GET("/photos")
    suspend fun photos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = "latest"
    ): List<PhotoBean>
}