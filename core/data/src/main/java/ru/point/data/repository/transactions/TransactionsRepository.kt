package ru.point.data.repository.transactions

import ru.point.data.model.dto.TransactionResponse

interface TransactionsRepository {

    suspend fun getTransactionsForPeriod(startDate: String, endDate: String): Result<List<TransactionResponse>>
}