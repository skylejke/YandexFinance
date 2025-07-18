package ru.point.impl.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.point.api.model.AccountDto
import ru.point.api.repository.AccountRepository
import ru.point.database.dao.AccountDao
import ru.point.database.dao.TransactionsDao
import ru.point.database.model.PendingAccountUpdate
import ru.point.impl.model.AccountUpdateRequest
import ru.point.impl.model.asAccountDto
import ru.point.impl.model.asAccountUpdateRequest
import ru.point.impl.service.AccountService
import ru.point.utils.network.InternetTracker
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение аккаунтов пользователя из сетевого источника.
 * Инкапсулирует работу с [AccountService] и переключение контекста на IO-поток.
 */
internal class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService,
    private val accountDao: AccountDao,
    private val transactionsDao: TransactionsDao,
    private val internetTracker: InternetTracker
) : AccountRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            internetTracker.online
                .filter { it }
                .collect {
                    syncPendingUpdate()
                }
        }
    }

    override suspend fun getAccounts() = withContext(Dispatchers.IO) {

        val cached: List<AccountDto> = accountDao.getAllAccounts().first()

        if (internetTracker.online.first()) {
            val remote = accountService.getAccounts()
                .map { accounts -> accounts.map { it.asAccountDto } }
                .getOrThrow()

            accountDao.insertAllAccounts(remote)
            Result.success(remote)
        } else {
            Result.success(cached)
        }
    }

    override suspend fun updateAccount(accountDto: AccountDto) = withContext(Dispatchers.IO) {
        accountDao.clearAccountsTable()
        accountDao.insertAllAccounts(listOf(accountDto))

        transactionsDao.updateAllAccountsInTransactions(
            name = accountDto.name,
            balance = accountDto.balance,
            currency = accountDto.currency
        )

        if (internetTracker.online.first()) {
            accountService.updateAccount(accountUpdateRequest = accountDto.asAccountUpdateRequest)
                .onSuccess {
                    accountDao.clearPendingAccountUpdateTable()
                }
                .onFailure {
                    accountDao.upsertPendingAccountUpdate(
                        PendingAccountUpdate(
                            name = accountDto.name,
                            balance = accountDto.balance,
                            currency = accountDto.currency
                        )
                    )
                }
        } else {
            accountDao.upsertPendingAccountUpdate(
                PendingAccountUpdate(
                    name = accountDto.name,
                    balance = accountDto.balance,
                    currency = accountDto.currency
                )
            )
        }

        Result.success(Unit)
    }

    override suspend fun syncPendingUpdate() {
        accountDao.getPendingAccountUpdate().first()?.let { pending ->
            accountService.updateAccount(
                accountUpdateRequest = AccountUpdateRequest(
                    name = pending.name,
                    balance = pending.balance,
                    currency = pending.currency
                )
            ).onSuccess {
                transactionsDao.updateAllAccountsInTransactions(
                    name = pending.name,
                    balance = pending.balance,
                    currency = pending.currency
                )
                accountDao.clearPendingAccountUpdateTable()
            }
        }
    }
}