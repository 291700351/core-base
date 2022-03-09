package io.github.lee.core.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.github.lee.core.ui.BuildConfig
import io.github.lee.core.util.LoggerUtil
import io.github.lee.core.vm.exceptions.ResponseThrowable
import io.github.lee.core.vm.exceptions.resultIsNull
import io.github.lee.core.vm.exceptions.systemError
import io.github.lee.core.vm.exceptions.unknownError
import io.github.lee.core.vm.status.PageStatusData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application),
    ViewModelLifecycle {
    protected val mContext
        get() = getApplication<Application>()

    val pageStatusLiveData = MutableLiveData<PageStatusData>()

    //===Desc:界面操作相关=====================================================================================
    fun showLoading() {
        pageStatusLiveData.postValue(PageStatusData.loading())
    }

    fun showSuccess() {
        pageStatusLiveData.postValue(PageStatusData.success())
    }

    fun showEmpty(msg: String? = "") =
        pageStatusLiveData.postValue(PageStatusData.empty(msg))


    open fun showError(msg: String? = "") =
        pageStatusLiveData.postValue(PageStatusData.error(msg))

    fun showProgress() =
        pageStatusLiveData.postValue(PageStatusData.progress())

    fun hideProgress() =
        pageStatusLiveData.postValue(PageStatusData.hideProgress())

    fun toast(msg: String) {
        pageStatusLiveData.postValue(PageStatusData.toast(msg))
    }

    fun longToast(msg: String) {
        pageStatusLiveData.postValue(PageStatusData.longToast(msg))
    }

    //===Desc:协程相关=====================================================================================
    fun launchOnUI(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(context) { block() }
    }

    suspend fun <T> withContextIO(
        block: suspend CoroutineScope.() -> T
    ) =
        withContext(Dispatchers.IO) { block }

    suspend fun <T> withContextMain(block: suspend CoroutineScope.() -> T) =
        withContext(Dispatchers.Main) { block }


    fun <T> launchResult(
        block: suspend CoroutineScope.() -> T?,
        success: suspend CoroutineScope.(T) -> Unit = { },
        error: suspend CoroutineScope.(ResponseThrowable) -> Unit = { }
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            launchOnUI {
                if (throwable is ResponseThrowable) {
                    error(throwable)
                } else {
                    if (BuildConfig.DEBUG) {
                        throwable.printStackTrace()
                    }
                    val msg = throwable.message
                    if (msg.isNullOrEmpty()) {
                        error(unknownError())
                    } else {
                        error(systemError(msg))
                    }
                }
            }
        }

        launchOnUI(handler) {
            val result = withContext(Dispatchers.IO) { block() }
            if (null == result) {
                error(resultIsNull())
            } else {
                success(result)
            }

        }
    }
}
