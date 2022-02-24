package io.github.lee.tmdb.model.repository

import io.github.lee.core.repository.BaseRepository
import io.github.lee.tmdb.model.api.TestApi
import javax.inject.Inject


class TestRepository @Inject constructor(
    private val api: TestApi,
) : BaseRepository() {

    suspend fun test() = api.test()


}