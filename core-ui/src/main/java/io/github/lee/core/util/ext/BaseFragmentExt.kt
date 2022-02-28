package io.github.lee.core.util.ext

import androidx.annotation.IdRes
import androidx.annotation.Px
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import io.github.lee.core.ui.BaseFragment
import io.github.lee.core.util.LoggerUtil

fun BaseFragment<*, *>.log(msg: Any?, checkJsonOrXml: Boolean = false) {
    if (null == msg) {
        LoggerUtil.w("The information you print is null, Please check it")
        return
    }
    LoggerUtil.log(msg, checkJsonOrXml)
}

inline fun <reified T> BaseFragment<*, *>.getArgumentValue(key: String, defaultValue: T?): T? {
    val bundle = arguments ?: return defaultValue
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

inline fun <reified T> BaseFragment<*, *>.getArgumentSerializable(key: String): T? {
    val bundle = arguments ?: return null
    val serializable = bundle.getSerializable(key)
    return if (serializable is T) {
        serializable
    } else {
        null
    }
}

fun <T> BaseFragment<*, *>.observe(liveData: LiveData<T>?, observer: Observer<T>) =
    liveData?.observe(viewLifecycleOwner, observer)

inline fun <reified T> BaseFragment<*, *>.getValueFromGraph(key: String, defaultValue: T?): T? {
    val value = findNavController().graph.arguments[key]?.defaultValue
    return when (T::class.java) {
        java.lang.Integer::class.java -> value as T
        java.lang.Long::class.java -> value as T
        java.lang.Short::class.java -> value as T
        java.lang.Double::class.java -> value as T
        java.lang.Float::class.java -> value as T
        java.lang.String::class.java -> value as T
        java.lang.Boolean::class.java -> value as T
        else -> defaultValue
    }
}

fun BaseFragment<*, *>.navigate(@IdRes resId: Int) {
    findNavController().navigate(resId)
}

fun BaseFragment<*, *>.popBackStack() {
    findNavController().popBackStack()
}

fun BaseFragment<*, *>.navigateUp() {
    findNavController().navigateUp()
}