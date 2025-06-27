package ru.point.transactions.history.domain.usecase

import ru.point.transactions.history.domain.model.TransactionHistoryItem

interface GetTransactionsHistoryUseCase {

    suspend operator fun invoke(isIncome: Boolean): Result<List<TransactionHistoryItem>>
}