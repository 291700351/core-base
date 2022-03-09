package io.github.lee.unsplash.global

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.github.lee.core.util.LoggerUtil
import io.github.lee.unsplash.BuildConfig


@HiltAndroidApp
class UnsplashApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LoggerUtil.init(this, BuildConfig.DEBUG, "TMDB_APPLICATION")
    }
}