package io.github.lee.core.util.ext

import android.view.View
import android.view.ViewGroup

val ViewGroup?.children: List<View>
    get() = if (null == this) {
        emptyList()
    } else {
        (0 until childCount).map {
            getChildAt(it)
        }
    }

//===Desc:=====================================================================================

val View?.viewParent: ViewGroup?
    get() = if (null == this) {
        null
    } else {
        val parent = this.parent
        if (parent is ViewGroup) {
            parent
        } else {
            null
        }
    }


fun View?.removeSelfFromParent() {
    if (null == this) {
        return
    }
    val parent = this.parent
    if (parent is ViewGroup) {
        parent.removeView(this)
    }
}
