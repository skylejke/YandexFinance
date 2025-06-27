package ru.point.data.repository.account

import ru.point.data.model.dto.AccountDto

interface AccountRepository {

    suspend fun getAccounts(): Result<List<AccountDto>>
}