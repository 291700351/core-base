package io.github.lee.unsplash.ui.detail

import androidx.core.view.ViewCompat
import coil.request.ImageRequest
import coil.request.ImageResult
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import io.github.lee.core.util.ba.loadImage
import io.github.lee.unsplash.R
import io.github.lee.unsplash.databinding.ItemPhotoDetailBinding
import io.github.lee.unsplash.model.domain.PhotoBean
import io.github.lee.unsplash.uitl.blurhash.BlurHash

class PhotoDetailAdapter(
    private val fragment: PhotoDetailFragment,
    private val blurHash: BlurHash
) :
    BaseQuickAdapter<PhotoBean, BaseDataBindingHolder<ItemPhotoDetailBinding>>(R.layout.item_photo_detail) {

    override fun convert(holder: BaseDataBindingHolder<ItemPhotoDetailBinding>, item: PhotoBean) {
        holder.dataBinding?.apply {
            ViewCompat.setTransitionName(pvPhoto, item.id)
            blurHash.execute(
                item.blur_hash?:"",
                (item.width / 10).toInt(), (item.height / 10).toInt()
            ) {
                pvPhoto.loadImage(item.urls?.full, 0, it,
                    null, object : ImageRequest.Listener {
                        override fun onSuccess(
                            request: ImageRequest,
                            metadata: ImageResult.Metadata
                        ) {
                            super.onSuccess(request, metadata)
                            fragment.startPostponedEnterTransition()
                        }
                    })
            }

        }
    }

}