package io.github.lee.core.vm.exceptions

import androidx.annotation.Keep


fun resultIsNull() =
    ResponseThrowable(1000, "Response data is null")

fun systemError(msg: String) =
    ResponseThrowable(1001, msg)

fun unknownError() =
    ResponseThrowable(1002, "Unknown mistake")


@Keep
class ResponseThrowable(code: Int, msg: String) : Throwable() {
    var code: Int = code
        private set
    var error: String = msg
        private set

    override fun toString(): String {
        return "ResponseThrowable(code=$code, msg='$error')"
    }


}