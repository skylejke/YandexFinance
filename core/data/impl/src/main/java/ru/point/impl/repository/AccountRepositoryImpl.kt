package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.api.repository.AccountRepository
import ru.point.impl.model.asAccountDto
import ru.point.impl.service.AccountService
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение аккаунтов пользователя из сетевого источника.
 * Инкапсулирует работу с [AccountService] и переключение контекста на IO-поток.
 */
internal class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService
) : AccountRepository {

    override suspend fun getAccounts() = withContext(Dispatchers.IO) {
        accountService.getAccounts().map { accounts ->
            accounts.map { it.asAccountDto }
        }
    }
}