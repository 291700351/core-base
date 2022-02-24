package io.github.lee.tmdb.main.movie

import android.content.Context
import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import io.github.lee.core.ui.BaseRefreshFragment
import io.github.lee.core.util.DataStoreUtil
import io.github.lee.core.util.ext.log
import io.github.lee.tmdb.databinding.ToolbarBinding
import kotlinx.coroutines.flow.first


val Context.dataStore by preferencesDataStore("hello")

@AndroidEntryPoint
class MainMovieFragment : BaseRefreshFragment<MainMovieVM>() {

    companion object {
        fun newInstance(): MainMovieFragment = MainMovieFragment()
    }

    override fun onCreateVM(): MainMovieVM =
        ViewModelProvider(this)[MainMovieVM::class.java]

    override fun onCreateToolbar(): Toolbar {
        return ToolbarBinding.inflate(layoutInflater).tl
    }

    override fun onSetViewData() {
        super.onSetViewData()
        vm?.launchOnUI {
            DataStoreUtil.instance(mContent.dataStore).savePreferences("lll", false)
            val result = DataStoreUtil.instance(mContent.dataStore).readPreferences<Boolean>("lll")
            log("result = ${result.first()}")
        }
    }
    //===Desc:=====================================================================================

    override fun onResume() {
        super.onResume()
        translucentStatusBar()
        view?.setBackgroundColor(Color.WHITE)
        getToolbarRoot().fitsSystemWindows = true
        lightStatusBars(true)
    }

}