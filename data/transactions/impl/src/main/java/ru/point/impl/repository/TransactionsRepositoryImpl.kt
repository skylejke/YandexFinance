package ru.point.impl.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.point.api.model.AccountStateDto
import ru.point.api.model.TransactionRequestDto
import ru.point.api.model.TransactionResponseDto
import ru.point.api.repository.TransactionsRepository
import ru.point.core.utils.BuildConfig
import ru.point.database.dao.AccountDao
import ru.point.database.dao.CategoriesDao
import ru.point.database.dao.PendingTransactionsDao
import ru.point.database.dao.TransactionsDao
import ru.point.database.model.PendingTransactionEntity
import ru.point.database.model.PendingTransactionOperation
import ru.point.impl.model.Transaction
import ru.point.impl.model.TransactionRequest
import ru.point.impl.model.asTransactionRequest
import ru.point.impl.model.asTransactionResponseDto
import ru.point.impl.service.TransactionsService
import ru.point.utils.extensions.endOfDayIso
import ru.point.utils.extensions.startOfDayIso
import ru.point.utils.network.InternetTracker
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение транзакций за указанный период из сетевого источника.
 * Делегирует обращение к [ru.point.impl.service.TransactionsService] и выполняет запрос в IO-контексте.
 */
internal class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    private val transactionsDao: TransactionsDao,
    private val pendingTransactionsDao: PendingTransactionsDao,
    private val accountDao: AccountDao,
    private val categoriesDao: CategoriesDao,
    private val internetTracker: InternetTracker
) : TransactionsRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            internetTracker.online
                .filter { it }
                .collect { syncPendingTransaction() }
        }
    }

    override suspend fun getTransactionsForPeriod(startDate: String, endDate: String) = withContext(Dispatchers.IO) {

        val cached: List<TransactionResponseDto> =
            transactionsDao.getTransactionsInPeriod(startDate.startOfDayIso(), endDate.endOfDayIso()).first()

        if (internetTracker.online.first()) {
            val remote = transactionsService
                .getTransactionsForPeriod(startDate = startDate, endDate = endDate)
                .map { list -> list.map { it.asTransactionResponseDto } }
                .getOrThrow()

            transactionsDao.clearPeriod(startDate.startOfDayIso(), endDate.endOfDayIso())
            transactionsDao.insertAllTransactions(remote)
            Result.success(remote)
        } else {
            Result.success(cached)
        }
    }

    override suspend fun getTransactionById(transactionId: Int) = withContext(Dispatchers.IO) {

        val cached = transactionsDao.getTransactionById(transactionId).first()

        if (internetTracker.online.first()) {
            val remoteDto = transactionsService.getTransactionById(transactionId)
                .map { it.asTransactionResponseDto }
                .getOrThrow()
            transactionsDao.insertTransaction(remoteDto)
            Result.success(remoteDto)
        } else {
            cached
                ?.let { Result.success(cached) }
                ?: Result.failure(IllegalStateException("No cached transaction"))
        }
    }

    override suspend fun createTransaction(transactionRequestDto: TransactionRequestDto) =
        withContext(Dispatchers.IO) {
            if (internetTracker.online.first()) {
                val remote = transactionsService
                    .createTransaction(transactionRequestDto.asTransactionRequest)
                    .getOrThrow()

                transactionsDao.insertTransaction(remote.asTransactionResponseDto())
            } else {
                val localId = -System.currentTimeMillis().toInt()
                val local = transactionRequestDto.asTransactionResponseDto(localId)
                transactionsDao.insertTransaction(local)
                pendingTransactionsDao.upsert(
                    PendingTransactionEntity(
                        transactionId = localId,
                        categoryId = transactionRequestDto.categoryId,
                        amount = transactionRequestDto.amount,
                        transactionDate = transactionRequestDto.transactionDate,
                        comment = transactionRequestDto.comment,
                        operation = PendingTransactionOperation.CREATE
                    )
                )
            }
            Result.success(Unit)
        }


    override suspend fun updateTransactionById(
        transactionId: Int,
        transactionRequestDto: TransactionRequestDto
    ) = withContext(Dispatchers.IO) {

        if (internetTracker.online.first()) {
            val remote = transactionsService
                .updateTransactionById(
                    transactionId,
                    transactionRequestDto.asTransactionRequest
                )
                .getOrThrow()

            transactionsDao.insertTransaction(remote.asTransactionResponseDto)
        } else {
            val local = transactionRequestDto.asTransactionResponseDto(transactionId)
            transactionsDao.insertTransaction(local)

            pendingTransactionsDao.upsert(
                PendingTransactionEntity(
                    transactionId = transactionId,
                    categoryId = transactionRequestDto.categoryId,
                    amount = transactionRequestDto.amount,
                    transactionDate = transactionRequestDto.transactionDate,
                    comment = transactionRequestDto.comment,
                    operation = PendingTransactionOperation.UPDATE
                )
            )
        }
        Result.success(Unit)
    }


    override suspend fun deleteTransactionById(transactionId: Int) =
        withContext(Dispatchers.IO) {
            transactionsDao.deleteTransactionById(transactionId)

            if (internetTracker.online.first()) {
                transactionsService.deleteTransactionById(transactionId).getOrThrow()
                pendingTransactionsDao.deleteById(transactionId)
            } else {
                pendingTransactionsDao.upsert(
                    PendingTransactionEntity(
                        transactionId = transactionId,
                        categoryId = null,
                        amount = null,
                        transactionDate = null,
                        comment = null,
                        operation = PendingTransactionOperation.DELETE
                    )
                )
            }
            Result.success(Unit)
        }

    override suspend fun syncPendingTransaction() {
        pendingTransactionsDao.getAllFlow().first().forEach {
            when (it.operation) {
                PendingTransactionOperation.CREATE -> {
                    transactionsService.createTransaction(it.asTransactionRequest).getOrThrow().let { transaction ->
                        transactionsDao.deleteTransactionById(it.transactionId)
                        transactionsDao.insertTransaction(
                            TransactionResponseDto(
                                id = transaction.id,
                                account = accountDao.getAllAccounts().first().map { accountDto ->
                                    AccountStateDto(
                                        id = transaction.accountId,
                                        name = accountDto.name,
                                        balance = accountDto.balance,
                                        currency = accountDto.currency,
                                    )
                                }.firstOrNull() ?: AccountStateDto(),
                                category = categoriesDao.getCategoryById(categoryId = it.categoryId!!).first(),
                                transactionDate = transaction.transactionDate,
                                comment = transaction.comment,
                                amount = transaction.amount,
                            )
                        )
                    }
                }

                PendingTransactionOperation.UPDATE -> {
                    transactionsService.updateTransactionById(
                        transactionId = it.transactionId,
                        transactionRequest = it.asTransactionRequest
                    ).getOrThrow()
                }

                PendingTransactionOperation.DELETE -> {
                    transactionsService.deleteTransactionById(it.transactionId).getOrThrow()
                }
            }
            pendingTransactionsDao.deleteById(it.id)
        }
    }

    private suspend fun Transaction.asTransactionResponseDto() =
        TransactionResponseDto(
            id = id,
            account = accountDao.getAllAccounts().first().map {
                AccountStateDto(
                    id = BuildConfig.ACCOUNT_ID.toInt(),
                    name = it.name,
                    balance = it.balance,
                    currency = it.currency,
                )
            }.first(),
            category = categoriesDao.getCategoryById(categoryId = categoryId).first(),
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
        )


    private suspend fun TransactionRequestDto.asTransactionResponseDto(id: Int) =
        TransactionResponseDto(
            id = id,
            account = accountDao.getAllAccounts().first().map {
                AccountStateDto(
                    id = BuildConfig.ACCOUNT_ID.toInt(),
                    name = it.name,
                    balance = it.balance,
                    currency = it.currency,
                )
            }.first(),
            category = categoriesDao.getCategoryById(categoryId = categoryId).first(),
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
        )

    private val PendingTransactionEntity.asTransactionRequest
        get() =
            TransactionRequest(
                accountId = BuildConfig.ACCOUNT_ID.toInt(),
                categoryId = categoryId!!,
                amount = amount!!,
                transactionDate = transactionDate!!,
                comment = comment
            )
}



