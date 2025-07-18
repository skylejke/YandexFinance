package ru.point.api.repository

import ru.point.api.model.AccountDto

interface AccountRepository {

    suspend fun getAccounts(): Result<List<AccountDto>>

    suspend fun updateAccount(accountDto: AccountDto): Result<Unit>

    suspend fun syncPendingUpdate()
}