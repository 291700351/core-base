package io.github.lee.unsplash.ui.main

import android.graphics.Bitmap
import androidx.core.view.ViewCompat
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.ImageResult
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import io.github.lee.core.util.ba.loadImage
import io.github.lee.unsplash.R
import io.github.lee.unsplash.databinding.ItemMainBinding
import io.github.lee.unsplash.model.domain.PhotoBean
import io.github.lee.unsplash.uitl.blurhash.BlurHash

class MainAdapter(private val blurHash: BlurHash) :
    BaseQuickAdapter<PhotoBean, BaseViewHolder>(R.layout.item_main),
    LoadMoreModule {
    private val bitmapCache = mutableMapOf<String, Bitmap?>()
    private val imageLoader: ImageLoader by lazy {
        ImageLoader.Builder(context)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .crossfade(300)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }


    override fun convert(holder: BaseViewHolder, item: PhotoBean) {
        val dataBinding = ItemMainBinding.bind(holder.itemView)


        ViewCompat.setTransitionName(dataBinding.rivPhoto, item.id)
        dataBinding.apply {
            val ration = item.width * 1F / item.height
            rivPhoto.ration = ration
            val cacheDrawable = bitmapCache[item.id]
            if (null == cacheDrawable) {
                blurHash.execute(
                    item.blur_hash?:"",
                    (item.width / 10).toInt(), (item.height / 10).toInt()
                ) {

                    //下载图片
                    rivPhoto.loadImage(
                        item.urls?.full, 300, it, null,
                        object : ImageRequest.Listener {
                            override fun onSuccess(
                                request: ImageRequest,
                                metadata: ImageResult.Metadata
                            ) {
                                val key = metadata.memoryCacheKey ?: return
                                val bitmap = imageLoader.memoryCache[key]
                                bitmapCache[item.id] = bitmap
                            }
                        })
                }
            } else {
                rivPhoto.setImageBitmap(cacheDrawable)
            }
        }

        dataBinding.executePendingBindings()

    }


}