package io.github.lee.core.ui

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.module.LoadMoreModule
import io.github.lee.core.ui.databinding.LayoutBaseRefreshBinding
import io.github.lee.core.util.ext.observe
import io.github.lee.core.vm.BaseRefreshViewModel
import io.github.lee.core.vm.status.RefreshStatus

open class BaseRefreshFragment<VM : BaseRefreshViewModel<*, *>>
    : BaseFragment<LayoutBaseRefreshBinding, VM>() {

    override fun onCreateVB(): LayoutBaseRefreshBinding =
        LayoutBaseRefreshBinding.inflate(layoutInflater)

    /**禁用或启用刷新*/
    protected var enableRefresh = true

    /**禁用或启用加载更多*/
    protected var enableLoadMore = true

    /*进入界面自动刷新*/
    protected var isAutoRefresh = true


//    protected lateinit var mAdapter: BindingAdapter


    //===Desc:=====================================================================================

    override fun onObserveData() {
        super.onObserveData()
        vm?.apply {
            observe(refreshLiveData) {
                when (it.status) {
                    RefreshStatus.STOP_REFRESH -> this@BaseRefreshFragment.stopRefresh()
                    RefreshStatus.LOAD_MODE_COMPLETE -> this@BaseRefreshFragment.loadMoreComplete()
                    RefreshStatus.LOAD_MORE_END -> this@BaseRefreshFragment.loadMoreEnd()
                    RefreshStatus.LOAD_MORE_FAIL -> this@BaseRefreshFragment.loadMoreFail()
                }
            }
        }
    }

    override fun onSetViewListener() {
        super.onSetViewListener()
        if (enableRefresh) {
            vb?.refresh?.setOnRefreshListener {
                vm?.onRefreshOrLoadMord(true)
            }
        }
        if (enableLoadMore) {
            vm?.apply {
                if (adapter is LoadMoreModule) {
                    adapter.loadMoreModule.setOnLoadMoreListener {
                        onRefreshOrLoadMord(false)
                    }
                }
            }
        }
    }

    override fun onSetViewData() {
        super.onSetViewData()
        vb?.apply {
            //设置刷新是否可用
            refresh.isEnabled = enableRefresh
            val lm = onCreateLayoutManager()
            if (null == lm) {
                refreshContent.layoutManager = LinearLayoutManager(mContent)
            } else {
                refreshContent.layoutManager = lm
            }
            refreshContent.adapter = vm?.adapter
            //            val holderAdapter = onHolderAdapter()
            //            mAdapter = if (null != holderAdapter) {
            //                refreshContent.setup(holderAdapter)
            //            } else {
            //                val adapter = BindingAdapter()
            //                refreshContent.adapter = adapter
            //                adapter
            //            }
        }
        vm?.apply {
            if (adapter is LoadMoreModule) {
                if (enableLoadMore) {
                    val loadMoreView = onCreateLoadMoreView()
                    if (null != loadMoreView) {
                        adapter.loadMoreModule.loadMoreView = loadMoreView
                    }
                    adapter.loadMoreModule.isAutoLoadMore = true
                    adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
                    adapter.loadMoreModule.isEnableLoadMore = true
                } else {
                    adapter.loadMoreModule.isEnableLoadMore = false
                }
            }

        }
        if (isAutoRefresh) {
            if (enableRefresh) {
                vb?.refresh?.isRefreshing = true
            }
            vm?.onRefreshOrLoadMord(true)
        }
    }

    //    protected open fun onHolderAdapter(): (BindingAdapter.(RecyclerView) -> Unit)? = null


    //===Desc:=====================================================================================

    protected open fun onCreateLayoutManager(): RecyclerView.LayoutManager? = null

    protected open fun onCreateLoadMoreView(): BaseLoadMoreView? = null

    //===Desc:刷新相关=====================================================================================

    protected fun stopRefresh() {
        vb?.refresh?.isRefreshing = false
    }

    //
    protected fun loadMoreComplete() =
        vm?.adapter?.loadMoreModule?.loadMoreComplete()

    protected fun loadMoreEnd(gone: Boolean = false) =
        vm?.adapter?.loadMoreModule?.loadMoreEnd(gone)

    protected fun loadMoreFail() =
        vm?.adapter?.loadMoreModule?.loadMoreFail()

    protected fun getSwipeRefreshLayout(): SwipeRefreshLayout? =
        vb?.refresh

    protected fun getRecyclerView(): RecyclerView? =
        vb?.refreshContent


    protected fun addItemDecoration(@NonNull decor: RecyclerView.ItemDecoration, index: Int = -1) {
        getRecyclerView()?.addItemDecoration(decor, index)
    }

    //===Desc:=====================================================================================

    protected fun addHeaderView(header: View) {
        vm?.adapter?.addHeaderView(header)
    }

    protected fun removeHeader(header: View) {
        vm?.adapter?.removeHeaderView(header)
    }

    protected fun removeAllHeader() {
        vm?.adapter?.removeAllHeaderView()
    }

    protected fun addFooterView(footer: View) {
        vm?.adapter?.addFooterView(footer)
    }

    protected fun removeFooter(footer: View) {
        vm?.adapter?.removeFooterView(footer)
    }

    protected fun removeAllFooter() {
        vm?.adapter?.removeAllFooterView()
    }

    protected fun addTop(view: View) {
        vb?.refreshTop?.apply {
            removeAllViews()
            addView(view)
        }
    }

    protected fun removeTop(view: View) {
        vb?.refreshTop?.removeView(view)
    }

    protected fun removeAllTop() {
        vb?.refreshTop?.removeAllViews()
    }

    protected fun addBottom(view: View) {
        vb?.refreshBottom?.apply {
            removeAllViews()
            addView(view)
        }
    }

    protected fun removeBottom(view: View) {
        vb?.refreshBottom?.removeView(view)
    }

    protected fun removeAllBottom() {
        vb?.refreshBottom?.removeAllViews()
    }
}