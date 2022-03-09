package io.github.lee.unsplash.global

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import androidx.annotation.Keep
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseActivity
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.unsplash.BuildConfig
import io.github.lee.unsplash.databinding.ActivityGlobalBinding


private const val TIME_DIFF = 2 * 1000L
private const val TIME_DELAY = 3 * 1000L

val Context.dataStore by preferencesDataStore(BuildConfig.APPLICATION_ID)


@Keep
@AndroidEntryPoint
class GlobalActivity : BaseActivity<ActivityGlobalBinding, BaseViewModel>() {
    private var isReady = false

    private var preClickTime = 0L
    override fun onCreateVB() = ActivityGlobalBinding.inflate(layoutInflater)
    //    override fun onBackPressed() {
    //        vb?.apply {
    //            val nc = findNavController(navHostFragment.id)
    //            val currentDestination = nc.currentDestination
    //            if (currentDestination is FragmentNavigator.Destination) {
    //                when (currentDestination.className) {
    //                    //SplashFragment::class.java.name -> {
    //                    //    if (BuildConfig.DEBUG) {
    //                    //        nc.navigate(R.id.action_splashFragment_to_mainFragment)
    //                    //    }
    //                    //}
    //                    //MainFragment::class.java.name -> {
    //                    //    val now = System.currentTimeMillis()
    //                    //    if ((now - preClickTime) > TIME_DIFF) {
    //                    //        toast(getString(R.string.txt_click_to_exit))
    //                    //        preClickTime = now
    //                    //    } else {
    //                    //        finish()
    //                    //    }
    //                    //}
    //                    else -> nc.popBackStack()
    //                }
    //            } else {
    //                super.onBackPressed()
    //            }
    //        }
    //    }

    override fun onSetViewData() {
        super.onSetViewData()
        //debug模式屏幕常亮
        if (BuildConfig.DEBUG) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        translucentStatusBar()
        lightStatusBars(true)

//        val view = findViewById<View>(android.R.id.content)
//        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
//            override fun onPreDraw(): Boolean {
//                if (isReady) {
//                    view.viewTreeObserver.removeOnPreDrawListener(this)
//                }
//                return isReady
//            }
//        })
//        Handler(Looper.getMainLooper())
//            .postDelayed({ isReady = true }, TIME_DELAY)
    }
}