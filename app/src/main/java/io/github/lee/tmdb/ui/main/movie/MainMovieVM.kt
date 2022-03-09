package io.github.lee.tmdb.ui.main.movie

import android.app.Application
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lee.core.util.ext.log
import io.github.lee.core.vm.BaseRefreshViewModel
import io.github.lee.tmdb.databinding.ABinding
import io.github.lee.tmdb.model.repository.TestRepository
import javax.inject.Inject

@HiltViewModel
class MainMovieVM @Inject constructor(
    application: Application, private val repo: TestRepository,
) : BaseRefreshViewModel<Any, BaseDataBindingHolder<ABinding>>(application) {


    override fun onRefreshOrLoadMord(isRefresh: Boolean) {
        super.onRefreshOrLoadMord(isRefresh)
        stopRefresh()
        launchResult(
            block = {
                for (i in 0..100) {
                    addData("Item <-> $i")
                }

                repo.test()
            },
            success = {
                log(it)
            },
            error = {
                log(it)
            }
        )
    }

    override fun onCreateAdapter(): BaseQuickAdapter<Any, BaseDataBindingHolder<ABinding>> =
        MainMovieAdapter()


}