package io.github.lee.tmdb.main

import android.content.Context
import android.graphics.Color
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseFragment
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.tmdb.R
import io.github.lee.tmdb.databinding.FragmentMainBinding
import io.github.lee.tmdb.main.movie.MainMovieFragment

var pages = mapOf(
    0 to MainMovieFragment.newInstance(),
    1 to C(),
    2 to D()
)

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, BaseViewModel>() {

    override fun onCreateVB() = FragmentMainBinding.inflate(layoutInflater)

    private var mediator: TabLayoutMediator? = null

    //===Desc:=====================================================================================

    override fun onSetViewData() {
        super.onSetViewData()
        translucentStatusBar()
        vb?.apply {
            val adapter = MainPageAdapter(this@MainFragment)
            vpContext.adapter = adapter
            vpContext.offscreenPageLimit = adapter.itemCount
            vpContext.isSaveEnabled = false
            mediator = TabLayoutMediator(tlBottom, vpContext) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.txt_movie)
                    1 -> tab.text = getString(R.string.txt_tv)
                    2 -> tab.text = getString(R.string.txt_personal)
                    else -> tab.text = getString(R.string.txt_movie)
                }
            }
            mediator?.attach()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //解决界面销毁 重复添加的问题，每次都重新创建，保证fragment不会重复添加
        pages = mapOf(
            0 to MainMovieFragment.newInstance(),
            1 to C(),
            2 to D()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
    }
}