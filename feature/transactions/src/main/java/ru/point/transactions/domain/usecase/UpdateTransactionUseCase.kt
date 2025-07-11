package ru.point.transactions.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.domain.model.TransactionRequestVo
import ru.point.transactions.domain.model.asTransactionRequestDto
import javax.inject.Inject

internal class UpdateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionsRepository,
) {

    suspend operator fun invoke(transactionId: Int, transactionRequestVo: TransactionRequestVo) =
        transactionRepository.updateTransactionById(
            transactionId = transactionId,
            transactionRequestDto = transactionRequestVo.asTransactionRequestDto
        )
}