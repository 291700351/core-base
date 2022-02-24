package io.github.lee.core.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt


/**随机生成RGB颜色*/
@ColorInt
fun prettyColor(): Int =
    Color.rgb((50..200).random(), (50..200).random(), (50..200).random())


fun gradientDrawable(
    @ColorInt color: Int,
    topLeftRadius: Float = 0F,
    topRightRadius: Float = 0F,
    bottomLeftRadius: Float = 0F,
    bottomRightRadius: Float = 0F
): GradientDrawable {
    val drawable = GradientDrawable()
    drawable.cornerRadii = arrayOf(
        topLeftRadius, topLeftRadius,
        topRightRadius, topRightRadius,
        bottomRightRadius, bottomRightRadius,
        bottomLeftRadius, bottomLeftRadius
    ).toFloatArray()
    drawable.setColor(color)
    return drawable
}

fun gradientDrawable(@ColorInt color: Int, radius: Float = 0F) {
    gradientDrawable(color, radius, radius, radius, radius)
}