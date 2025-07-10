package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.api.model.TransactionRequestDto
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.model.asTransactionRequest
import ru.point.impl.model.asTransactionResponseDto
import ru.point.impl.service.TransactionsService
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение транзакций за указанный период из сетевого источника.
 * Делегирует обращение к [ru.point.impl.service.TransactionsService] и выполняет запрос в IO-контексте.
 */
internal class TransactionsRepositoryImpl @Inject constructor(
    private val transactionsService: TransactionsService
) : TransactionsRepository {

    override suspend fun getTransactionsForPeriod(startDate: String, endDate: String) =
        withContext(Dispatchers.IO) {
            transactionsService.getTransactionsForPeriod(
                startDate = startDate,
                endDate = endDate
            ).map { transactions ->
                transactions.map { it.asTransactionResponseDto }
            }
        }

    override suspend fun createTransaction(transactionRequestDto: TransactionRequestDto) = withContext(Dispatchers.IO) {
        transactionsService.createTransaction(transactionRequest = transactionRequestDto.asTransactionRequest).map { }
    }

    override suspend fun getTransactionById(transactionId: Int) = withContext(Dispatchers.IO) {
        transactionsService.getTransactionById(transactionId = transactionId).map {
            it.asTransactionResponseDto
        }
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