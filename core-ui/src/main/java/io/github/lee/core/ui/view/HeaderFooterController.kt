package io.github.lee.core.ui.view

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding

abstract class HeaderFooterController<T, VB : ViewDataBinding?>(val context: Context) {

    val vb: VB? by lazy {
        val createViewBinding = onCreateViewBinding() ?: return@lazy null
        val vbName = createViewBinding::class.simpleName
        val baseName = ViewDataBinding::class.simpleName
        if (vbName.equals(baseName)) {
            null
        } else {
            createViewBinding
        }
    }

    val rootView: View? by lazy { vb?.root }

    //===Desc:=====================================================================================

    protected abstract fun onCreateViewBinding(): VB?

    //===Desc:=====================================================================================

    /**子类重写该方法进行界面渲染*/
    open fun render(data: T?) {
    }
}