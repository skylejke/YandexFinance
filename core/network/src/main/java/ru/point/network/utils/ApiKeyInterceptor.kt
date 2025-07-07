package ru.point.network.utils

import okhttp3.Interceptor
import okhttp3.Response
import ru.point.network.BuildConfig

/**
 * Interceptor, добавляющий токен авторизации к каждому HTTP-запросу.
 *
 * Подставляет заголовок `Authorization: Bearer <API_KEY>` из [BuildConfig.API_KEY]
 * для аутентификации при обращении к защищённому API.
 */
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