package io.github.lee.core.util.ext

import android.content.Context
import android.util.TypedValue
import androidx.annotation.Px
import java.io.File

fun Float.dp(context: Context): Float {
    File("").toURI()
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        context.resources.displayMetrics
    )
}

fun Int.dp(context: Context): Float {
    return toFloat().dp(context)
}

fun Double.dp(context: Context): Float {
    return toFloat().dp(context)
}

//===Desc:sp=====================================================================================
fun Float.sp(context: Context): Float =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        context.resources.displayMetrics
    )


fun Int.sp(context: Context): Float {
    return toFloat().sp(context)
}

fun Double.sp(context: Context): Float {
    return toFloat().sp(context)
}

//===Desc:=====================================================================================
@Px
fun Float.dp2px(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

@Px
fun Int.dp2px(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

@Px
fun Double.dp2px(context: Context): Int {
    val scale: Float = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}
