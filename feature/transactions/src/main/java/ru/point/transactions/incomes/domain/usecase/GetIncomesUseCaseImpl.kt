package ru.point.transactions.incomes.domain.usecase

import ru.point.data.repository.transactions.TransactionsRepository
import ru.point.transactions.incomes.domain.model.asIncome
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetIncomesUseCaseImpl @Inject constructor(private val transactionsRepository: TransactionsRepository) : GetIncomesUseCase {

    override suspend fun invoke() = transactionsRepository.getTransactionsForPeriod(
        startDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
        endDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    ).map { transactionResponses ->
        transactionResponses.filter { it.category.isIncome }.map { it.asIncome }
    }
}