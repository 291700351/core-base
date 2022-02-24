package io.github.lee.tmdb.global.di.net.interceptors

import okhttp3.Interceptor
import okhttp3.Response

private const val API_READ_ACCESS_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZjVjMDk0NjExNWRlZjE5MjY1MTBjY2YwMmM2OGYxMCIsInN1YiI6IjVmOThmYjdiZmI1Mjk5MDAzODMyYmIyMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.vkl9-24n6wcpt-0SLyhExf7eI7eoA3kXQpPl2hzGd2Y"

class ParameterInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val build = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $API_READ_ACCESS_TOKEN")
            .build()
        return chain.proceed(build)
    }
}