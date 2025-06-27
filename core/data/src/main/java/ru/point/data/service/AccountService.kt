package ru.point.data.service

import retrofit2.http.GET
import ru.point.data.model.dto.AccountDto

interface AccountService {

    @GET("api/v1/accounts")
    suspend fun getAccounts(): Result<List<AccountDto>>
}