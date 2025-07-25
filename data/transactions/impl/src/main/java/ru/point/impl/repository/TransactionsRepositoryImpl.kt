package ru.point.impl.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.point.api.model.AccountStateDto
import ru.point.api.model.TransactionRequestDto
import ru.point.api.model.TransactionResponseDto
import ru.point.api.repository.TransactionsRepository
import ru.point.core.utils.BuildConfig
import ru.point.database.dao.AccountDao
import ru.point.database.dao.CategoriesDao
import ru.point.database.dao.PendingTransactionsDao
import ru.point.database.dao.SyncMetadataDao
import ru.point.database.dao.TransactionsDao
import ru.point.database.model.PendingTransactionEntity
import ru.point.database.model.PendingTransactionOperation
import ru.point.database.model.SyncMetadata
import ru.point.dto.CategoryDto
import ru.point.impl.model.Transaction
import ru.point.impl.model.TransactionRequest
import ru.point.impl.model.asTransactionRequest
import ru.point.impl.model.asTransactionResponseDto
import ru.point.impl.service.TransactionsService
import ru.point.utils.extensions.endOfDayIso
import ru.point.utils.extensions.startOfDayIso
import ru.point.utils.network.InternetTracker
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject


private const val TRANSACTIONS_SYNC_TYPE = "transactions"

internal class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    private val transactionsDao: TransactionsDao,
    private val pendingTransactionsDao: PendingTransactionsDao,
    private val accountDao: AccountDao,
    private val categoriesDao: CategoriesDao,
    private val metadataDao: SyncMetadataDao,
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

            metadataDao.upsert(SyncMetadata(TRANSACTIONS_SYNC_TYPE, System.currentTimeMillis()))

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
        pendingTransactionsDao.getAllFlow().first()
            .filter { it.operation == PendingTransactionOperation.CREATE }
            .forEach { pending ->
                try {
                    val server = transactionsService
                        .createTransaction(pending.asTransactionRequest)
                        .getOrThrow()

                    transactionsDao.deleteTransactionById(pending.transactionId)
                    transactionsDao.insertTransaction(server.asTransactionResponseDto())
                    pendingTransactionsDao.replaceTransactionId(
                        oldId = pending.transactionId,
                        newId = server.id
                    )
                    pendingTransactionsDao.deleteById(pending.id)
                    metadataDao.upsert(SyncMetadata(TRANSACTIONS_SYNC_TYPE, System.currentTimeMillis()))
                } catch (_: Exception) {
                }
            }

        pendingTransactionsDao.getAllFlow().first()
            .filter { it.operation == PendingTransactionOperation.UPDATE }
            .forEach { pending ->
                try {
                    transactionsService
                        .updateTransactionById(
                            transactionId = pending.transactionId,
                            transactionRequest = pending.asTransactionRequest
                        )
                        .getOrThrow()

                    val fresh = transactionsService
                        .getTransactionById(pending.transactionId)
                        .map { it.asTransactionResponseDto }
                        .getOrThrow()
                    transactionsDao.insertTransaction(fresh)

                    pendingTransactionsDao.deleteById(pending.id)
                    metadataDao.upsert(SyncMetadata(TRANSACTIONS_SYNC_TYPE, System.currentTimeMillis()))
                } catch (_: Exception) {
                }
            }

        pendingTransactionsDao.getAllFlow().first()
            .filter { it.operation == PendingTransactionOperation.DELETE }
            .forEach { pending ->
                try {
                    transactionsService
                        .deleteTransactionById(pending.transactionId)
                        .getOrThrow()

                    pendingTransactionsDao.deleteById(pending.id)
                    metadataDao.upsert(SyncMetadata(TRANSACTIONS_SYNC_TYPE, System.currentTimeMillis()))
                } catch (e: HttpException) {
                    if (e.code() == 404) {
                        pendingTransactionsDao.deleteById(pending.id)
                        metadataDao.upsert(SyncMetadata(TRANSACTIONS_SYNC_TYPE, System.currentTimeMillis()))
                    }
                } catch (_: Exception) {
                }
            }
    }

    override suspend fun getLastSync() =
        metadataDao.getLastSync(TRANSACTIONS_SYNC_TYPE)

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
            category = categoriesDao.getCategoryById(categoryId = categoryId).firstOrNull() ?: CategoryDto(),
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
            updatedAt = updatedAt
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
            category = categoriesDao.getCategoryById(categoryId = categoryId).firstOrNull() ?: CategoryDto(),
            amount = amount,
            transactionDate = transactionDate,
            comment = comment,
            updatedAt = Instant
                .now()
                .truncatedTo(ChronoUnit.SECONDS)
                .toString()
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