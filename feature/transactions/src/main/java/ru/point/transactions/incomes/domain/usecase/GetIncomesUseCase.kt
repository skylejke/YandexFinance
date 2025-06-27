package ru.point.transactions.incomes.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.incomes.domain.model.asIncome
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetIncomesUseCase @Inject constructor(private val transactionsRepository: TransactionsRepository) {

    suspend operator fun invoke() = transactionsRepository.getTransactionsForPeriod(
        startDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
        endDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    ).map { transactionResponses ->
        transactionResponses.filter { it.category.isIncome }.map { it.asIncome }
    }
}