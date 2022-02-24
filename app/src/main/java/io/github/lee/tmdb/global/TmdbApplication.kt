package io.github.lee.tmdb.global

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.lee.core.util.LoggerUtil
import io.github.lee.tmdb.BuildConfig


@HiltAndroidApp
class TmdbApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoggerUtil.init(this, BuildConfig.DEBUG, "TMDB_APPLICATION")
    }
}