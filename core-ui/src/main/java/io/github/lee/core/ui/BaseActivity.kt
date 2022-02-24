package io.github.lee.core.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.github.lee.core.ui.databinding.LayoutBaseHoverBinding
import io.github.lee.core.ui.databinding.LayoutBaseLinearBinding
import io.github.lee.core.util.ext.log
import io.github.lee.core.util.ext.observe
import io.github.lee.core.vm.BaseViewModel
import io.github.lee.core.vm.status.PageStatus
import qiu.niorgai.StatusBarCompat

open class BaseActivity<VB : ViewDataBinding, VM : BaseViewModel>
    : AppCompatActivity() {

    private val mToast by lazy {
        Toast.makeText(mContent, "", Toast.LENGTH_SHORT)
    }
    protected val mContent: Context by lazy { this }

    @Suppress("UNCHECKED_CAST")
    protected val vb: VB? by lazy { onCreateVB() }

    protected val vm: VM? by lazy { onCreateVM() }

    protected open fun onCreateVB(): VB? = null
    protected open fun onCreateVM(): VM? = null


    /**Toolbar是否悬浮在内容上*/
    protected open var isHover = false
    private lateinit var toolbarContainer: FrameLayout
    private lateinit var contentContainer: FrameLayout

    protected var mToolbar: Toolbar? = null
        private set
    protected var loadingVb: ViewDataBinding? = null
        private set
    protected var emptyVb: ViewDataBinding? = null
        private set
    protected var errorVb: ViewDataBinding? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInitData(savedInstanceState)
        if (isFinishing) {
            log("The activity ${javaClass.simpleName} is finishing, No UI rendering")
            return
        }

        //隐藏ActionBar
        hideActionbar()

        //设置根view显示
        val rootView = if (isHover) {
            val inflate = LayoutBaseHoverBinding.inflate(layoutInflater)
            toolbarContainer = inflate.coreToolbarContainer
            contentContainer = inflate.coreContentContainer
            inflate
        } else {
            val inflate = LayoutBaseLinearBinding.inflate(layoutInflater)
            toolbarContainer = inflate.coreToolbarContainer
            contentContainer = inflate.coreContentContainer
            inflate
        }
        setContentView(rootView.root)

        toolbarContainer.removeAllViews()
        toolbarContainer.visibility = View.GONE
        contentContainer.removeAllViews()

        //设置toolbar
        mToolbar = onCreateToolbar()?.also {
            toolbarContainer.addView(it)
            toolbarContainer.visibility = View.VISIBLE
            it.title = ""
            setSupportActionBar(it)
            if (isHover) {
                toolbarContainer.fitsSystemWindows = true
            }
        }
        //设置loading
        loadingVb = onCreateLoading()?.also {
            it.lifecycleOwner = this
            contentContainer.addView(it.root)
        }

        vb?.also {
            it.lifecycleOwner = this
            contentContainer.addView(it.root)
        }

        emptyVb = onCreateEmpty()?.also {
            it.lifecycleOwner = this
            contentContainer.addView(it.root)
        }

        errorVb = onCreateError()?.also {
            it.lifecycleOwner = this
            contentContainer.addView(it.root)
        }
        //===Desc:根据子类实现的view方法  显示不同状态的界面=====================================================================================
        if (null != loadingVb) {
            showLoading()
        } else {
            if (null != vb) {
                showSuccess()
            } else {
                if (null != emptyVb) {
                    showEmpty(getString(R.string.core_data_empty))
                } else {
                    if (null != errorVb) {
                        showError(getString(R.string.core_data_error))
                    }
                }
            }
        }
        if (null != vm) {
            lifecycle.addObserver(vm!!)
        }
        //===Desc:=====================================================================================
        onObserveData()
        onSetViewListener()
        onSetViewData()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingVb?.unbind()
        vb?.unbind()
        emptyVb?.unbind()
        errorVb?.unbind()
        if (null != vm) {
            lifecycle.removeObserver(vm!!)
        }
    }

    //===Desc:=====================================================================================
    protected open fun onInitData(savedInstanceState: Bundle?) {

    }

    protected open fun onObserveData() {
        vm?.apply {
            observe(pageStatusLiveData) {
                val msg = it.msg
                when (it.status) {
                    PageStatus.LOADING -> this@BaseActivity.showLoading()
                    PageStatus.SUCCESS -> this@BaseActivity.showSuccess()
                    PageStatus.EMPTY -> this@BaseActivity.showEmpty(msg)
                    PageStatus.ERROR -> this@BaseActivity.showError(msg)
                    PageStatus.PROGRESS -> this@BaseActivity.showProgress()
                    PageStatus.HIDE_PROGRESS -> this@BaseActivity.hideProgress()
                    PageStatus.TOAST -> this@BaseActivity.toast(msg!!)
                    PageStatus.LONG_TOAST -> this@BaseActivity.toast(msg!!, true)
                }
            }
        }
    }

    protected open fun onSetViewListener() {}
    protected open fun onSetViewData() {}

    //===Desc:界面渲染相关=====================================================================================
    protected open fun onCreateToolbar(): Toolbar? = null
    protected open fun onCreateLoading(): ViewDataBinding? = null
    protected open fun onCreateEmpty(): ViewDataBinding? = null
    protected open fun onCreateError(): ViewDataBinding? = null

    //===Desc:界面状态相关=====================================================================================

    protected open fun showLoading() {
        loadingVb?.root?.visibility = View.VISIBLE

        vb?.root?.visibility = View.GONE
        emptyVb?.root?.visibility = View.GONE
        errorVb?.root?.visibility = View.GONE
    }

    protected open fun showSuccess() {
        vb?.root?.visibility = View.VISIBLE

        loadingVb?.root?.visibility = View.GONE
        emptyVb?.root?.visibility = View.GONE
        errorVb?.root?.visibility = View.GONE
    }

    protected open fun showEmpty(msg: String? = "") {
        emptyVb?.root?.visibility = View.VISIBLE

        loadingVb?.root?.visibility = View.GONE
        vb?.root?.visibility = View.GONE
        errorVb?.root?.visibility = View.GONE
    }

    protected open fun showError(msg: String? = "") {
        errorVb?.root?.visibility = View.VISIBLE

        loadingVb?.root?.visibility = View.GONE
        vb?.root?.visibility = View.GONE
        emptyVb?.root?.visibility = View.GONE
    }

    protected open fun toast(msg: String, isLong: Boolean? = false) {
        mToast.setText(msg)
        mToast.duration = if (isLong == true) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
        mToast.show()
    }

    protected open fun showProgress() {

    }

    protected open fun hideProgress() {

    }

    ///////////////////////////////////////////////////////////////////////////
    // 一些常用方法
    ///////////////////////////////////////////////////////////////////////////
    //===Desc:=====================================================================================
    /**隐藏Actionbar*/
    protected fun hideActionbar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    //===Desc:=====================================================================================

    protected fun getContentRoot(): FrameLayout =
        contentContainer

    protected fun getToolbarRoot(): FrameLayout =
        toolbarContainer

    protected fun setToolbarLeftImage(
        @DrawableRes resId: Int,
        listener: View.OnClickListener? = null
    ) {
        mToolbar?.apply {
            setNavigationIcon(resId)
            setNavigationOnClickListener(listener)
        }
    }

    protected fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(getContentRoot().id, fragment)
            .commitNowAllowingStateLoss()
    }


    /**
     * @param color 状态栏颜色
     * @param alpha -1-255
     */
    protected fun setStatusBarColor(
        @ColorInt color: Int,
        @IntRange(from = -1, to = 255) alpha: Int = -1
    ) = if (alpha == -1) {
        StatusBarCompat.setStatusBarColor(this, color)
    } else {
        StatusBarCompat.setStatusBarColor(this, color, alpha)
    }


    protected fun translucentStatusBar(hideStatusBarBackground: Boolean = false) =
        StatusBarCompat.translucentStatusBar(this, hideStatusBarBackground)

    protected fun lightStatusBars(isBlack: Boolean = true) =
        if (isBlack) {
            StatusBarCompat.changeToLightStatusBar(this)
        } else {
            StatusBarCompat.cancelLightStatusBar(this)
        }


    protected fun lightNavigationBars(isBlack: Boolean = true) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val root = vb?.root ?: return
        val windowInsetsController = ViewCompat.getWindowInsetsController(root)
        windowInsetsController?.isAppearanceLightNavigationBars = isBlack

    }

    protected fun showStatusBar(show: Boolean = true) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }
        val root = vb?.root ?: return
        val windowInsetsController = ViewCompat.getWindowInsetsController(root)
        if (show) {
            windowInsetsController?.show(WindowInsetsCompat.Type.statusBars())
        } else {
            windowInsetsController?.hide(WindowInsetsCompat.Type.statusBars())
        }
    }

}