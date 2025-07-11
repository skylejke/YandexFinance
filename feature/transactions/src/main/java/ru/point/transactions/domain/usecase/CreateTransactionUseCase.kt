package ru.point.transactions.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.domain.model.TransactionRequestVo
import ru.point.transactions.domain.model.asTransactionRequestDto
import javax.inject.Inject

internal class CreateTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionsRepository
) {

    suspend operator fun invoke(transactionRequestVo: TransactionRequestVo) =
        transactionRepository.createTransaction(transactionRequestDto = transactionRequestVo.asTransactionRequestDto)
}