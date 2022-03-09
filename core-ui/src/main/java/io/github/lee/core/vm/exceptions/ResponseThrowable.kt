package io.github.lee.core.vm.exceptions

import android.content.Context
import androidx.annotation.Keep
import io.github.lee.core.ui.R


fun resultIsNull() =
    ResponseThrowable(1000, "Response data is null")

fun systemError(msg: String) =
    ResponseThrowable(1001, msg)

fun unknownError() =
    ResponseThrowable(1002, "Unknown mistake")


@Keep
class ResponseThrowable(code: Int, msg: String) : Throwable(msg) {
    var code: Int = code
        private set
    var error: String = msg
        private set

    override fun toString(): String =
        "Error code : %d, Error message : %s".format(code, error)


    fun formatString(context: Context) =
        context.getString(R.string.core_response_throwable_format).format(code, error)

}