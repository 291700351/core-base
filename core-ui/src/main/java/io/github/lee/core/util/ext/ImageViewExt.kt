package io.github.lee.core.util.ext

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import coil.transform.Transformation


fun ImageView.loadImageWithTransform(
    url: String? = null,
    crossFadeTime: Int?,
    placeholder: Drawable?,
    error: Drawable?,
    listener: ImageRequest.Listener? = null,
    vararg transform: Transformation
): Disposable? {
    if (TextUtils.isEmpty(url)) {
        if (null != error) {
            setImageDrawable(error)
        }
        return null
    }
    return load(url) {
        if (null != crossFadeTime && crossFadeTime > 0) {
            crossfade(true)
            crossfade(crossFadeTime)
        }
        if (null != placeholder) {
            placeholder(placeholder)
        }
        if (transform.isNotEmpty()) {
            transformations(*transform)
        }
        if (null != error) {
            error(error)
        }
        listener(listener)
    }
}