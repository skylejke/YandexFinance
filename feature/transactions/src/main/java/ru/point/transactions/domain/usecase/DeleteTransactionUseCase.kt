package ru.point.transactions.domain.usecase

import ru.point.api.repository.TransactionsRepository
import javax.inject.Inject

internal class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionsRepository
) {

    suspend operator fun invoke(transactionId: Int) =
        transactionRepository.deleteTransactionById(transactionId = transactionId)
}