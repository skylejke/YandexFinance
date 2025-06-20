package ru.point.yandexfinance.core.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.point.yandexfinance.BuildConfig
import ru.point.yandexfinance.core.data.model.TransactionResponse

interface TransactionsService {
    @GET("api/v1/transactions/account/{accountId}/period")
    suspend fun getTransactionsForPeriod(
        @Path("accountId") accountId: Int = BuildConfig.ACCOUNT_ID.toInt(),
        @Query("startDate") start: String,
        @Query("endDate") end: String
    ): Result<List<TransactionResponse>>
}