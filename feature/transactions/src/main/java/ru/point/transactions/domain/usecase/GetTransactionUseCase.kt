package ru.point.transactions.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.domain.model.asTransactionVo
import javax.inject.Inject

internal class GetTransactionUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke(transactionId: Int) =
        transactionsRepository.getTransactionById(transactionId = transactionId).map {
            it.asTransactionVo
        }
}