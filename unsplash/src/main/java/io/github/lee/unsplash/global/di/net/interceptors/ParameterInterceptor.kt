package io.github.lee.unsplash.global.di.net.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val API_READ_ACCESS_TOKEN =
    "RKmJFYDGTPRGdZsHjl1mzZ0F96rvXwo7GYhmfyPh368"

class ParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val build = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID $API_READ_ACCESS_TOKEN")
            .addHeader("Accept-Version", "v1")
            .build()
        return chain.proceed(build)
    }
}