package ru.point.impl.service

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.point.core.utils.BuildConfig
import ru.point.impl.model.Transaction
import ru.point.impl.model.TransactionRequest
import ru.point.impl.model.TransactionResponse

internal interface TransactionsService {

    @GET("api/v1/transactions/account/{accountId}/period")
    suspend fun getTransactionsForPeriod(
        @Path("accountId") accountId: Int = BuildConfig.ACCOUNT_ID.toInt(),
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Result<List<TransactionResponse>>

    @POST("api/v1/transactions")
    suspend fun createTransaction(
        @Body transactionRequest: TransactionRequest,
    ): Result<Transaction>

    @GET("api/v1/transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") transactionId: Int,
    ): Result<TransactionResponse>

    @PUT("api/v1/transactions/{id}")
    suspend fun updateTransactionById(
        @Path("id") transactionId: Int,
        @Body transactionRequest: TransactionRequest,
    ): Result<TransactionResponse>
    
    @DELETE("api/v1/transactions/{id}")
    suspend fun deleteTransactionById(
        @Path("id") transactionId: Int,
    ): Result<Unit>
}