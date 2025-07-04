package ru.point.utils.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor, повторяющий HTTP-запрос при получении ответа с кодом 500 (Internal Server Error).
 *
 * Выполняет до [MAX_RETRIES] попыток с задержкой [DELAY_MS] между ними.
 * Используется для повышения устойчивости сетевых запросов.
 */
class RetryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var tries = 0
        var response = chain.proceed(chain.request())

        while (response.code() == 500 && tries < MAX_RETRIES) {
            tries++
            response.close()
            Thread.sleep(DELAY_MS)
            response = chain.proceed(chain.request())
        }

        return response
    }

    companion object {
        private const val MAX_RETRIES: Int = 3
        private const val DELAY_MS: Long = 2_000
    }
}