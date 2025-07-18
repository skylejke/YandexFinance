package ru.point.api.repository

import kotlinx.coroutines.flow.Flow
import ru.point.api.model.TransactionRequestDto
import ru.point.api.model.TransactionResponseDto

interface TransactionsRepository {

    suspend fun getTransactionsForPeriod(startDate: String, endDate: String): Result<List<TransactionResponseDto>>

    suspend fun createTransaction(transactionRequestDto: TransactionRequestDto): Result<Unit>

    suspend fun getTransactionById(transactionId: Int): Result<TransactionResponseDto>

    suspend fun updateTransactionById(transactionId: Int, transactionRequestDto: TransactionRequestDto): Result<Unit>

    suspend fun deleteTransactionById(transactionId: Int): Result<Unit>

    suspend fun syncPendingTransaction()

    suspend fun getLastSync(): Flow<Long>
}