package io.github.lee.core.util

import android.content.Context
import androidx.annotation.Nullable
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

object LoggerUtil {
    fun init(
        context: Context, isDebug: Boolean? = false,
        tag: String? = context.packageName
    ) {
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)
            .methodCount(0)
            .tag(tag)
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return isDebug ?: false
            }
        })
    }

    //===Desc:Debug=====================================================================================
    fun d(msg: Any?) {
        com.orhanobut.logger.Logger.d(msg)
    }

    fun d(msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.d(msg, *args)
    }

    //===Desc:Error=====================================================================================
    fun e(@Nullable t: Throwable?, msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.e(t, msg, *args)
    }

    fun e(msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.e(msg, *args)
    }

    fun e(msg: Any?) {
        com.orhanobut.logger.Logger.e(msg?.toString() ?: "The information you printed is null")
    }

    //===Desc:Warning=====================================================================================
    fun w(msg: Any?) {
        com.orhanobut.logger.Logger.w(msg?.toString() ?: "The information you printed is null")
    }

    fun w(msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.w(msg, *args)
    }

    //===Desc:Verbose=====================================================================================
    fun v(msg: Any?) {
        com.orhanobut.logger.Logger.v(msg?.toString() ?: "The information you printed is null")
    }

    fun v(msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.v(msg, *args)
    }

    //===Desc:Information=====================================================================================
    fun i(msg: Any?) {
        com.orhanobut.logger.Logger.i(msg?.toString() ?: "The information you printed is null")
    }

    fun i(msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.i(msg, *args)
    }

    //===Desc:What a terrible failure=====================================================================================
    fun wtf(msg: Any?) {
        com.orhanobut.logger.Logger.wtf(msg?.toString() ?: "The information you printed is null")
    }

    fun wtf(msg: String, vararg args: Any) {
        com.orhanobut.logger.Logger.wtf(msg, *args)
    }


    fun json(json: String?) {
        Logger.json(json)
    }

    fun xml(xml: String?) {
        Logger.xml(xml)
    }

    fun log(msg: Any?) {
        e(msg)
    }

}