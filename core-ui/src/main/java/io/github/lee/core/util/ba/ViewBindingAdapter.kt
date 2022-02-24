package io.github.lee.core.util.ba

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import io.github.lee.core.util.gradientDrawable

@BindingAdapter(
    value = [
        "color",
        "top_left_radius",
        "top_right_radius",
        "bottom_left_radius",
        "bottom_right_radius"
    ],
    requireAll = false
)
fun View.viewBackground(
    color: String? = "",
    top_left_radius: Float? = 0F,
    top_right_radius: Float? = 0F,
    bottom_left_radius: Float? = 0F,
    bottom_right_radius: Float? = 0F
) {
    val c = if (color?.isEmpty() == true) {
        Color.TRANSPARENT
    } else {
        Color.parseColor(color)
    }
    this.background = gradientDrawable(
        c,
        top_left_radius ?: 0F,
        top_right_radius ?: 0F,
        bottom_left_radius ?: 0F,
        bottom_right_radius ?: 0F
    )
}

@BindingAdapter(value = ["gone"])
fun View.gone(gone: Boolean? = false) {
    if (gone == true) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["invisible"])
fun View.invisible(invisible: Boolean? = false) {
    if (invisible == true) {
        this.visibility = View.INVISIBLE
    } else {
        this.visibility = View.VISIBLE
    }
}

@BindingAdapter(
    value = ["view_click"]
)
fun View.viewClick(func: ((View) -> Unit)?) {
    if (null == func) {
        return
    }
    this.setOnClickListener {
        func(it)
    }

}