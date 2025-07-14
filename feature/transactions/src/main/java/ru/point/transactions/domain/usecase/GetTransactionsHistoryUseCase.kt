package ru.point.transactions.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.domain.model.asTransactionHistoryItem
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class GetTransactionsHistoryUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke(
        isIncome: Boolean,
        startDate: String = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
        endDate: String = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    ) =
        transactionsRepository.getTransactionsForPeriod(
            startDate = startDate,
            endDate = endDate
        ).map { transactionResponses ->
            transactionResponses.filter { it.category.isIncome == isIncome }
                .sortedByDescending { Instant.parse(it.transactionDate) }
                .map { it.asTransactionHistoryItem }
        }
}