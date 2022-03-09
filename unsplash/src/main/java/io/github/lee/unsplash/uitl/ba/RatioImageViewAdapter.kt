package io.github.lee.unsplash.uitl.ba

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import io.github.lee.core.ui.view.RatioImageView
import io.github.lee.core.util.ba.loadImage
import io.github.lee.unsplash.model.domain.PhotoBean
import io.github.lee.unsplash.uitl.blurhash.BlurHash

//class RatioImageViewAdapter {
//}

@BindingAdapter(
    value = [
        "load_photo",
        "crossfade_time",
        "error_res"], requireAll = false
)
fun RatioImageView.load(
    photo: PhotoBean?,
    crossFadeTime: Int?,
    error: Drawable?
) {
    if (null == photo) {
        setImageDrawable(error)
        return
    }
    ration = photo.width / photo.height


    BlurHash(context, lruSize = 20, punch = 1F)
        .execute(photo.blur_hash?:"", (photo.width / 10).toInt(), (photo.height / 10).toInt()) {
            loadImage(photo.urls?.full, crossFadeTime, it, error)
        }
}