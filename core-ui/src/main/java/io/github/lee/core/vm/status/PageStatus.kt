package io.github.lee.core.vm.status

import androidx.annotation.Keep

@Keep
enum class PageStatus {
    LOADING,
    SUCCESS,
    EMPTY,
    ERROR,
    PROGRESS,
    HIDE_PROGRESS,
    TOAST,
    LONG_TOAST
}

@Keep
data class PageStatusData(val status: PageStatus, val msg: String? = "") {
    companion object {
        fun loading() = PageStatusData(PageStatus.LOADING)
        fun success() = PageStatusData(PageStatus.SUCCESS)
        fun empty(msg: String? = "") = PageStatusData(PageStatus.EMPTY, msg)
        fun error(msg: String? = "") = PageStatusData(PageStatus.ERROR, msg)
        fun progress() = PageStatusData(PageStatus.PROGRESS)
        fun hideProgress() = PageStatusData(PageStatus.HIDE_PROGRESS)
        fun toast(msg: String) = PageStatusData(PageStatus.TOAST, msg)
        fun longToast(msg: String) = PageStatusData(PageStatus.LONG_TOAST, msg)
    }
}
