package ru.point.transactions.history.domain.usecase

import ru.point.data.repository.transactions.TransactionsRepository
import ru.point.transactions.history.domain.model.asTransactionHistoryItem
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTransactionsHistoryUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : GetTransactionsHistoryUseCase {

    override suspend fun invoke(isIncome: Boolean) = transactionsRepository.getTransactionsForPeriod(
        startDate = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
        endDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    ).map { transactionResponses ->
        transactionResponses.filter { it.category.isIncome == isIncome }
            .sortedByDescending { Instant.parse(it.transactionDate) }
            .map { it.asTransactionHistoryItem }
    }
}