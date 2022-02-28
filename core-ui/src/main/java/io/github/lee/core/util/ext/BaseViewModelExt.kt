package io.github.lee.core.util.ext

import io.github.lee.core.util.LoggerUtil
import io.github.lee.core.vm.BaseViewModel

fun BaseViewModel.log(msg: Any?, checkJsonOrXml: Boolean = false) {
    if (null == msg) {
        LoggerUtil.w("The information you print is null, Please check it")
        return
    }
    LoggerUtil.log(msg, checkJsonOrXml)
}