package io.github.lee.unsplash.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.lee.core.util.ext.log
import io.github.lee.core.vm.BaseRefreshViewModel
import io.github.lee.unsplash.model.domain.PhotoBean
import io.github.lee.unsplash.model.repository.PhotoRepository
import io.github.lee.unsplash.uitl.blurhash.BlurHash
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    application: Application,
    private val repo: PhotoRepository
) : BaseRefreshViewModel<PhotoBean, BaseViewHolder>(application) {


    /**详情界面刷新数据使用*/
    val listPhoto = MutableLiveData<List<PhotoBean>>()

    private val blurHash: BlurHash by lazy {
        BlurHash(mContext, lruSize = 20, punch = 1F)
    }

    override fun onCreateAdapter() = MainAdapter(blurHash)

    //===Desc:=====================================================================================

    override fun onRefreshOrLoadMord(isRefresh: Boolean) {
        super.onRefreshOrLoadMord(isRefresh)
        currentPage = if (isRefresh) {
            1
        } else {
            currentPage + 1
        }

        launchResult(
            block = {
                val i = 1 / 0

                val result = repo.photos(page = currentPage)
                repo.insert(result)
                result
            },
            success = {
                listPhoto.postValue(it)
                if (isRefresh) {
                    val list = mutableListOf<PhotoBean>()
                    it.forEach { item ->
                        if (!getData().contains(item)) {
                            list.add(item)
                        }
                    }
                    addData(list, 0)
                    stopRefresh()
                } else {
                    addData(it)
                    loadMoreComplete()
                }
            },
            error = {
                log(it.formatString(mContext))
                if (isRefresh) {
                    showError(it.formatString(mContext))
                    stopRefresh()
                } else {
                    loadMoreFail()
                    currentPage--
                }
            }
        )
    }

}