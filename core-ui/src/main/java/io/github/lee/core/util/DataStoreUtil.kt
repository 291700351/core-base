package io.github.lee.core.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreUtil private constructor() {
    lateinit var dataStore: DataStore<Preferences>
        private set

    companion object {
        private var instance: DataStoreUtil? = null
        fun instance(dataStore: DataStore<Preferences>): DataStoreUtil {
            if (null == instance) {
                synchronized(DataStoreUtil::class) {
                    if (null == instance) {
                        instance = DataStoreUtil()
                    }
                }
            }
            instance?.dataStore = dataStore
            return instance!!
        }
    }


    //===Desc:Preferences=====================================================================================
    suspend fun <T : Any> savePreferences(key: String, value: T) {
        when (value) {
            is String -> dataStore.edit { it[stringPreferencesKey(key)] = value }
            is Int -> dataStore.edit { it[intPreferencesKey(key)] = value }
            is Long -> dataStore.edit { it[longPreferencesKey(key)] = value }
            is Double -> dataStore.edit { it[doublePreferencesKey(key)] = value }
            is Float -> dataStore.edit { it[floatPreferencesKey(key)] = value }
            is Boolean -> dataStore.edit { it[booleanPreferencesKey(key)] = value }
            else -> throw IllegalArgumentException("Type not supported: ${value::class.java}")
        }
    }

    suspend fun saveStringSetPreferences(key: String, value: Set<String>) {
        dataStore.edit {
            it[stringSetPreferencesKey(key)] = value
        }
    }

    inline fun <reified T> readPreferences(key: String): Flow<T?> {
        return when (T::class) {
            String::class -> dataStore.data.map { it[stringPreferencesKey(key)] as T }
            Int::class -> dataStore.data.map { it[intPreferencesKey(key)] as T }
            Long::class -> dataStore.data.map { it[longPreferencesKey(key)] as T }
            Double::class -> dataStore.data.map { it[doublePreferencesKey(key)] as T }
            Float::class -> dataStore.data.map { it[floatPreferencesKey(key)] as T }
            Boolean::class -> dataStore.data.map { it[booleanPreferencesKey(key)] as T }
            else -> throw IllegalArgumentException("Type not supported: ${T::class.java}")
        }
    }

    fun readStringSetPreferences(key: String): Flow<Set<String>> {
        return dataStore.data.map {
            it[stringSetPreferencesKey(key)] ?: emptySet()
        }
    }

    //===Desc:=====================================================================================

}