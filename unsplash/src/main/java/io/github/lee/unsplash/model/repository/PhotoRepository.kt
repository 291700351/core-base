package io.github.lee.unsplash.model.repository

import io.github.lee.core.repository.BaseRepository
import io.github.lee.unsplash.model.api.PhotoApi
import io.github.lee.unsplash.model.dao.PhotoDao
import io.github.lee.unsplash.model.dao.UrlDao
import io.github.lee.unsplash.model.domain.PhotoBean
import javax.inject.Inject

enum class PhotoOrderBy(val value: String) {
    LATEST("latest"), OLDEST("oldest"), POPULAR("popular")
}

class PhotoRepository @Inject constructor(
    private val api: PhotoApi,
) : BaseRepository() {
    @Inject
    lateinit var photoDao: PhotoDao

    @Inject
    lateinit var urlDao: UrlDao

    suspend fun insert(photos: List<PhotoBean>) {
        photos.forEach {
            val db = photoDao.queryById(it.id)
            if (null == db) {
                photoDao.insert(it)
                if (null != it.urls) {
                    it.urls.photoId = it.id
                    urlDao.insert(it.urls)
                }
            }
        }
    }
    //===Desc:=====================================================================================

    suspend fun photos(
        page: Int = 1,
        size: Int = 20,
        orderBy: PhotoOrderBy = PhotoOrderBy.LATEST
    ) = api.photos(page, size, orderBy.value)
}