package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.model.asTransactionDto
import ru.point.impl.service.TransactionsService
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение транзакций за указанный период из сетевого источника.
 * Делегирует обращение к [TransactionsService] и выполняет запрос в IO-контексте.
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
                transactions.map { it.asTransactionDto }
            }
        }
}