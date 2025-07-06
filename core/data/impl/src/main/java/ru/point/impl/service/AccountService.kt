package ru.point.impl.service

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.point.core.utils.BuildConfig
import ru.point.impl.model.Account
import ru.point.impl.model.AccountUpdateRequest

internal interface AccountService {

    @GET("api/v1/accounts")
    suspend fun getAccounts(): Result<List<Account>>

    @PUT("api/v1/accounts/{accountId}")
    suspend fun updateAccount(
        @Path("accountId") accountId: Int = BuildConfig.ACCOUNT_ID.toInt(),
        @Body accountUpdateRequest: AccountUpdateRequest,
    ): Result<Account>
}