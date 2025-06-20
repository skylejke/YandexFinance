package ru.point.yandexfinance.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.point.yandexfinance.BuildConfig

class ApiKeyInterceptor: Interceptor {
    private val token = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}