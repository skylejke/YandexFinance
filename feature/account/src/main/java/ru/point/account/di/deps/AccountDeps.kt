package ru.point.account.di.deps

import ru.point.api.repository.AccountRepository
import ru.point.api.repository.TransactionsRepository

interface AccountDeps {

    val accountRepository: AccountRepository

    val transactionRepository: TransactionsRepository
}