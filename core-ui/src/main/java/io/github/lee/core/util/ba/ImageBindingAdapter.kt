package io.github.lee.core.util.ba

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.request.Disposable
import coil.request.ImageRequest
import io.github.lee.core.util.ext.loadImageWithTransform

/**
 * XML中使用 error_res="@{@drawable/layer_default_img_load}"  不能使用@mipmap
 */
@BindingAdapter(
    value = ["load_url",
        "crossfade_time",
        "placeholder_res",
        "error_res",
        "download_listener"],
    requireAll = false
)
fun ImageView.loadImage(
    url: String? = null,
    crossFadeTime: Int?,
    placeholder: Drawable?,
    error: Drawable?,
    listener: ImageRequest.Listener? = null
): Disposable? =
    loadImageWithTransform(url, crossFadeTime, placeholder, error, listener)

