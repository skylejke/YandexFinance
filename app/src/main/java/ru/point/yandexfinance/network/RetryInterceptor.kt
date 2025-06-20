package ru.point.yandexfinance.network

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var res: Response
        var tries = 0
        while (tries++ < MAX_RETRIES) {
            res = chain.proceed(chain.request())
            if (res.code != 500) break
            Thread.sleep(DELAY_MS)
        }
        return res
    }

    companion object {
        private const val MAX_RETRIES: Int = 3
        private const val DELAY_MS: Long = 2_000
    }
}