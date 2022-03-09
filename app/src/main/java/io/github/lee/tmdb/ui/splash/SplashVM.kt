package io.github.lee.tmdb.ui.splash

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.tmdb.BuildConfig
import javax.inject.Inject

const val TIME_DELAY = 5 * 1000L

@HiltViewModel
class SplashVM @Inject constructor(application: Application) : BaseViewModel(application) {
    val countdownFinish = MutableLiveData<Boolean>()
    private val countDownTimer = object : CountDownTimer(TIME_DELAY, 1000) {
        override fun onTick(p0: Long) {

        }

        override fun onFinish() {
            countdownFinish.postValue(true)
        }
    }

    //===Desc:=====================================================================================
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        countDownTimer.start()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        countDownTimer.cancel()
    }

    //===Desc:=====================================================================================

    fun countDownFinish() {
        if (BuildConfig.DEBUG) {
            countdownFinish.postValue(true)
            countDownTimer.cancel()
        }
    }
}