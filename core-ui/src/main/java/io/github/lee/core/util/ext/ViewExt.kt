package io.github.lee.core.util.ext

import android.view.View
import android.view.ViewGroup


fun View?.removeSelfFromParent() {
    if (null == this) {
        return
    }
    val parent = this.parent
    if (parent is ViewGroup) {
        parent.removeView(this)
    }
}