package io.github.lee.core.vm.status

import androidx.annotation.Keep


@Keep
enum class RefreshStatus {
    STOP_REFRESH,
    LOAD_MODE_COMPLETE,
    LOAD_MORE_END,
    LOAD_MORE_FAIL
}

@Keep
data class RefreshStatusData(val status: RefreshStatus) {
    companion object {
        fun stopRefresh() = RefreshStatusData(RefreshStatus.STOP_REFRESH)
        fun loadModeComplete() = RefreshStatusData(RefreshStatus.LOAD_MODE_COMPLETE)
        fun loadMoreEnd() = RefreshStatusData(RefreshStatus.LOAD_MORE_END)
        fun loadMoreFail() = RefreshStatusData(RefreshStatus.LOAD_MORE_FAIL)
    }
}