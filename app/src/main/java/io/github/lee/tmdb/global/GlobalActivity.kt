package io.github.lee.tmdb.global

import android.view.WindowManager
import androidx.annotation.Keep
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseActivity
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.tmdb.BuildConfig
import io.github.lee.tmdb.R
import io.github.lee.tmdb.databinding.ActivityGlobalBinding
import io.github.lee.tmdb.main.MainFragment
import io.github.lee.tmdb.splash.SplashFragment

private const val TIME_DIFF = 2 * 1000L

@Keep
@AndroidEntryPoint
class GlobalActivity : BaseActivity<ActivityGlobalBinding, BaseViewModel>() {
    private var preClickTime = 0L
    override fun onCreateVB() = ActivityGlobalBinding.inflate(layoutInflater)
    override fun onBackPressed() {
        vb?.apply {
            val nc = findNavController(navHostFragment.id)
            val currentDestination = nc.currentDestination
            if (currentDestination is FragmentNavigator.Destination) {
                when (currentDestination.className) {
                    SplashFragment::class.java.name -> {
                        if (BuildConfig.DEBUG) {
                            nc.navigate(R.id.action_splashFragment_to_mainFragment)
                        }
                    }
                    MainFragment::class.java.name -> {
                        val now = System.currentTimeMillis()
                        if ((now - preClickTime) > TIME_DIFF) {
                            toast(getString(R.string.txt_click_to_exit))
                            preClickTime = now
                        } else {
                            finish()
                        }
                    }
                    else -> nc.popBackStack()
                }
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        //debug模式屏幕常亮
        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        translucentStatusBar()
    }
}