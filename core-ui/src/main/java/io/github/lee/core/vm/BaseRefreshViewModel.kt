package io.github.lee.core.vm


import android.app.Application
import androidx.annotation.IntRange
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.github.lee.core.vm.status.RefreshStatusData

/**下拉刷新  上拉加载相关的ViewModel*/
abstract class BaseRefreshViewModel<T, VH : BaseViewHolder>(application: Application) :
    BaseViewModel(application) {

    protected var currentPage = 1

    /**刷新  加载更多状态*/
    val refreshLiveData = MutableLiveData<RefreshStatusData>()

    val adapter: BaseQuickAdapter<T, VH> by lazy {
        onCreateAdapter()
    }

    //===Desc:=====================================================================================

    /**子类实现adapter*/
    abstract fun onCreateAdapter(): BaseQuickAdapter<T, VH>


    open fun onRefreshOrLoadMord(isRefresh: Boolean = false) {

    }

    //===Desc:=====================================================================================
    protected fun stopRefresh() =
        refreshLiveData.postValue(RefreshStatusData.stopRefresh())

    protected fun loadMoreComplete() =
        refreshLiveData.postValue(RefreshStatusData.loadModeComplete())

    protected fun loadMoreEnd() =
        refreshLiveData.postValue(RefreshStatusData.loadMoreEnd())

    protected fun loadMoreFail() =
        refreshLiveData.postValue(RefreshStatusData.loadMoreFail())

    //===Desc:=====================================================================================
    fun setNewData(list: List<T>) {
        adapter.setNewInstance(list.toMutableList())
    }

    fun addData(data: T, @IntRange(from = 0) position: Int? = null) {
        if (null == data) {
            return
        }
        if (null == position) {
            adapter.addData(data)
        } else {
            adapter.addData(position, data)
        }
    }

    fun addData(data: List<T>, @IntRange(from = 0) position: Int? = null) {
        if (null == position) {
            adapter.addData(data)
        } else {
            adapter.addData(position, data)
        }
    }

    fun remove(data: T) {
        val index = adapter.data.indexOf(data)
        if (index == -1) {
            return
        }
        removeAt(index)
    }

    fun removeAt(@IntRange(from = 0) position: Int) {
        adapter.removeAt(position)
    }

    fun getData(): List<T> = adapter.data

    fun getDataAt(@IntRange(from = 0) position: Int): T =
        getData()[position]

}