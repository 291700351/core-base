package io.github.lee.core.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.github.lee.core.util.DataStoreUtil
import kotlinx.coroutines.flow.Flow


open class BaseRepository {

    //===Desc:DataStore读写基本数据类型=====================================================================================
    inline fun <reified T> readPreferences(
        dataStore: DataStore<Preferences>,
        key: String
    ): Flow<T?> {
        return DataStoreUtil.instance(dataStore)
            .readPreferences<T>(key)
    }

    suspend fun <T : Any> savePreferences(
        dataStore: DataStore<Preferences>,
        key: String,
        value: T
    ) {
        DataStoreUtil.instance(dataStore)
            .savePreferences(key, value)
    }

    //===Desc:Room数据库读写=====================================================================================


}