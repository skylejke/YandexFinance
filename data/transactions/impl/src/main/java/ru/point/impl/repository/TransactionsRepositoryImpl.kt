package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ru.point.api.model.TransactionRequestDto
import ru.point.api.model.TransactionResponseDto
import ru.point.api.repository.TransactionsRepository
import ru.point.database.dao.TransactionsDao
import ru.point.impl.model.asTransactionRequest
import ru.point.impl.model.asTransactionResponseDto
import ru.point.impl.service.TransactionsService
import ru.point.utils.network.InternetTracker
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение транзакций за указанный период из сетевого источника.
 * Делегирует обращение к [ru.point.impl.service.TransactionsService] и выполняет запрос в IO-контексте.
 */
internal class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService,
    private val transactionsDao: TransactionsDao,
    private val internetTracker: InternetTracker
) : TransactionsRepository {

    override suspend fun getTransactionsForPeriod(startDate: String, endDate: String) = withContext(Dispatchers.IO) {

        val cached: List<TransactionResponseDto> =
            transactionsDao.getTransactionsInPeriod(startDate, endDate).first()

        if (internetTracker.online.first()) {
            val remote = transactionsService
                .getTransactionsForPeriod(startDate = startDate, endDate = endDate)
                .map { list -> list.map { it.asTransactionResponseDto } }
                .getOrThrow()

            transactionsDao.clearPeriod(startDate, endDate)
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

    override suspend fun createTransaction(transactionRequestDto: TransactionRequestDto) = withContext(Dispatchers.IO) {
        transactionsService.createTransaction(transactionRequest = transactionRequestDto.asTransactionRequest).map { }
    }

    override suspend fun updateTransactionById(
        transactionId: Int,
        transactionRequestDto: TransactionRequestDto
    ) = withContext(Dispatchers.IO) {
        transactionsService.updateTransactionById(
            transactionId = transactionId,
            transactionRequest = transactionRequestDto.asTransactionRequest
        ).map { }
    }

    override suspend fun deleteTransactionById(transactionId: Int) = withContext(Dispatchers.IO) {
        transactionsService.deleteTransactionById(transactionId = transactionId)
    }
}