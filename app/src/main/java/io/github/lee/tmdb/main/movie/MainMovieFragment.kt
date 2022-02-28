package io.github.lee.tmdb.main.movie

import android.graphics.Color
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseRefreshFragment
import io.github.lee.core.util.ext.sp
import io.github.lee.tmdb.R
import io.github.lee.tmdb.databinding.ToolbarGlobalCenterTitleBinding


@AndroidEntryPoint
class MainMovieFragment : BaseRefreshFragment<MainMovieVM>() {

    companion object {
        fun newInstance(): MainMovieFragment = MainMovieFragment()
    }

    override fun onCreateVM(): MainMovieVM =
        ViewModelProvider(this)[MainMovieVM::class.java]

    override fun onCreateToolbar(): Toolbar {
        val tl = ToolbarGlobalCenterTitleBinding.inflate(layoutInflater)
        tl.title = "HeLLo"
        tl.titleColor = Color.parseColor("#FF00FF")
//        tl.titleSize = 18.sp(mContent)
        tl.tl.setBackgroundColor(Color.YELLOW)
        return tl.tl
    }

    override fun onSetViewData() {
        super.onSetViewData()
        val tv = TextView(mContent)
        tv.text = getString(R.string.app_name)
        addHeaderView(tv)
    }

    //===Desc:=====================================================================================

    override fun onResume() {
        super.onResume()
        translucentStatusBar()
        view?.setBackgroundColor(Color.WHITE)
        getContentRoot().fitsSystemWindows = true
        lightStatusBars(true)
    }

}