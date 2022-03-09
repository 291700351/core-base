package io.github.lee.tmdb.ui.splash

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseFragment
import io.github.lee.core.util.ext.observe
import io.github.lee.tmdb.BuildConfig
import io.github.lee.tmdb.R
import io.github.lee.tmdb.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashVM>() {

    override fun onCreateVB(): FragmentSplashBinding =
        FragmentSplashBinding.inflate(layoutInflater)

    override fun onCreateVM(): SplashVM =
        ViewModelProvider(this)[SplashVM::class.java]

    override fun onObserveData() {
        super.onObserveData()
        observe(vm?.countdownFinish) {
            if (it) {
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
            }
        }
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        if (BuildConfig.DEBUG) {
            vb?.clickListener = object : Function1<View, Unit> {
                override fun invoke(p1: View) {
                    vm?.countDownFinish()
                }
            }
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        translucentStatusBar()
        lightStatusBars(true)
    }
}