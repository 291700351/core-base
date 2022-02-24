package io.github.lee.core.util.ext

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import coil.load
import coil.transform.Transformation


fun ImageView.loadImageWithTransform(
    url: String? = null,
    crossFadeTime: Int?,
    placeholder: Drawable?,
    error: Drawable?,
    vararg transform: Transformation
) {
    if (TextUtils.isEmpty(url)) {
        if (null != error) {
            setImageDrawable(error)
        }
        return
    }
    load(url) {
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
    }
}