package ru.point.transactions.history.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.history.domain.model.asTransactionHistoryItem
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetTransactionsHistoryUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke(isIncome: Boolean) = transactionsRepository.getTransactionsForPeriod(
        startDate = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
        endDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    ).map { transactionResponses ->
        transactionResponses.filter { it.category.isIncome == isIncome }
            .sortedByDescending { Instant.parse(it.transactionDate) }
            .map { it.asTransactionHistoryItem }
    }
}