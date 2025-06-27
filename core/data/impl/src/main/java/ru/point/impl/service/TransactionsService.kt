package ru.point.impl.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.point.impl.BuildConfig
import ru.point.impl.model.Transaction

interface TransactionsService {

    @GET("api/v1/transactions/account/{accountId}/period")
    suspend fun getTransactionsForPeriod(
        @Path("accountId") accountId: Int = BuildConfig.ACCOUNT_ID.toInt(),
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Result<List<Transaction>>
}