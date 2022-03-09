package io.github.lee.unsplash.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseRefreshFragment
import io.github.lee.unsplash.R
import io.github.lee.unsplash.databinding.LayoutGlobalErrorBinding
import io.github.lee.unsplash.ui.detail.PhotoDetailFragment


var currentShare = 0

@AndroidEntryPoint
class MainFragment : BaseRefreshFragment<MainVM>() {
    private val error by lazy {
        LayoutGlobalErrorBinding.inflate(layoutInflater)
    }

    override fun onCreateVM(): MainVM = ViewModelProvider(this)[MainVM::class.java]

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        val spanCount =
            if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                4
            } else {
                2
            }
        return object : StaggeredGridLayoutManager(spanCount, VERTICAL) {
            override fun isAutoMeasureEnabled(): Boolean = true
        }.also {
            it.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            getRecyclerView()?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    it.invalidateSpanAssignments()
                }

            })
        }
    }
    //===Desc:=====================================================================================

    override fun showError(msg: String?) {
        vm?.adapter?.setEmptyView(error.root)
        error.errorMsg = msg
        lightStatusBars(true)
        showSuccess()
    }

    override fun onInitData(savedInstanceState: Bundle?) {
        super.onInitData(savedInstanceState)
        enableRefresh = true
        enableLoadMore = true
        isAutoRefresh = true
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        vm?.adapter?.setOnItemClickListener { _, view, position ->
            val photoList = vm?.adapter?.data ?: emptyList()
            val name = vm?.adapter?.data?.get(position)?.id ?: ""
            currentShare = position
            PhotoDetailFragment.mainNavigateDetail(
                this, photoList, position,
                view.findViewById<View>(R.id.rivPhoto) to name
            )
        }

        setExitSharedElementCallback(object : androidx.core.app.SharedElementCallback() {
            override fun onMapSharedElements(
                names: MutableList<String>?,
                sharedElements: MutableMap<String, View>?
            ) {
                super.onMapSharedElements(names, sharedElements)
                if (null == names || names.isEmpty()) {
                    return
                }
                val name = names[0]
                val selectedHolder =
                    getRecyclerView()?.findViewHolderForAdapterPosition(currentShare)
                        ?: return
                val itemView = selectedHolder.itemView
                sharedElements?.put(name, itemView.findViewById(R.id.rivPhoto))
            }
        })
    }

    override fun onSetViewData() {
        super.onSetViewData()
        translucentStatusBar(true)
        postponeEnterTransition()
        (view?.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        //避免刷新闪烁问题
        val itemAnim = getRecyclerView()?.itemAnimator
        if (itemAnim is SimpleItemAnimator) {
            itemAnim.supportsChangeAnimations = false
        }
        itemAnim?.changeDuration = 0
    }
}