package ru.point.yandexfinance.core.data.service

import retrofit2.http.GET
import ru.point.yandexfinance.core.data.model.AccountDto

interface AccountService {
    @GET("api/v1/accounts")
    suspend fun getAccounts(): Result<List<AccountDto>>
}