package ru.point.api.repository

import ru.point.api.model.AccountDto

interface AccountRepository {

    suspend fun getAccounts(): Result<List<AccountDto>>
}