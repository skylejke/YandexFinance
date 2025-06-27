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
        lateinit var res: Response
        var tries = 0
        while (tries++ < MAX_RETRIES) {
            res = chain.proceed(chain.request())
            if (res.code() != 500) break
            Thread.sleep(DELAY_MS)
        }
        return res
    }

    companion object {
        private const val MAX_RETRIES: Int = 3
        private const val DELAY_MS: Long = 2_000
    }
}