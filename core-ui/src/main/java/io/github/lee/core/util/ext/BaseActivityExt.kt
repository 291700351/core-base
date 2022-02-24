package io.github.lee.core.util.ext

import android.app.Activity
import android.content.Intent
import androidx.annotation.Px
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import io.github.lee.core.ui.BaseActivity
import io.github.lee.core.ui.BaseFragment
import io.github.lee.core.util.LoggerUtil

fun BaseActivity<*, *>.log(msg: Any?) {
    if (null == msg) {
        LoggerUtil.w("The information you print is null, Please check it")
        return
    }
    LoggerUtil.e(msg)
}

inline fun <reified T> BaseActivity<*, *>.getBundleValue(key: String, defaultValue: T?): T? {
    val bundle = intent.extras ?: return defaultValue
    return when (T::class.java) {
        java.lang.Integer::class.java -> bundle.getInt(key) as T
        java.lang.Long::class.java -> bundle.getLong(key) as T
        java.lang.Short::class.java -> bundle.getShort(key) as T
        java.lang.Double::class.java -> bundle.getDouble(key) as T
        java.lang.Float::class.java -> bundle.getFloat(key) as T
        java.lang.String::class.java -> bundle.getString(key) as T
        java.lang.Boolean::class.java -> bundle.getBoolean(key) as T
        else -> defaultValue
    }

}

inline fun <reified T> BaseActivity<*, *>.getBundleSerializable(key: String): T? {
    val bundle = intent.extras ?: return null
    val serializable = bundle.getSerializable(key)
    return if (serializable is T) {
        serializable
    } else {
        null
    }
}

@Px
fun BaseActivity<*, *>.dp2px(dipValue: Float): Int {
    val scale: Float = resources.displayMetrics.density
    return (dipValue * scale + 0.5f).toInt()
}

fun <T> BaseActivity<*, *>.observe(liveData: LiveData<T>?, observer: Observer<T>) =
    liveData?.observe(this, observer)


inline fun <reified TARGET : Activity> BaseActivity<*, *>.navigate(vararg pairs: Pair<String, Any?>) {
    val starter = Intent(this, TARGET::class.java)
    starter.putExtras(bundleOf(*pairs))
    startActivity(starter)
}

fun BaseFragment<*, *>.putValueToGraph(vararg pairs: Pair<String, Any?>) {
    val graph = findNavController().graph
    pairs.forEach {
        val key = it.first
        val value = it.second
        if (null != value) {
            val argument = NavArgument.Builder()
                .setDefaultValue(value)
                .build()
            graph.addArgument(key, argument)
        }
    }

}
