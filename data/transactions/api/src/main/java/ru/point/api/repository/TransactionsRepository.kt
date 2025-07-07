package ru.point.api.repository

import ru.point.api.model.TransactionDto

interface TransactionsRepository {

    suspend fun getTransactionsForPeriod(startDate: String, endDate: String): Result<List<TransactionDto>>
}