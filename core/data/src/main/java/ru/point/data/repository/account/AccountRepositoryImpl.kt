package ru.point.data.repository.account

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.data.service.AccountService
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение аккаунтов пользователя из сетевого источника.
 * Инкапсулирует работу с [AccountService] и переключение контекста на IO-поток.
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService
) : AccountRepository {

    override suspend fun getAccounts() = withContext(Dispatchers.IO) {
        accountService.getAccounts()
    }
}