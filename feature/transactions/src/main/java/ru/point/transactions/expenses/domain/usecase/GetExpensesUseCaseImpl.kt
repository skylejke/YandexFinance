package ru.point.transactions.expenses.domain.usecase

import ru.point.data.repository.transactions.TransactionsRepository
import ru.point.transactions.expenses.domain.model.asExpense
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetExpensesUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) : GetExpensesUseCase {

    override suspend fun invoke() = transactionsRepository.getTransactionsForPeriod(
        startDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
        endDate = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
    ).map { transactionResponses ->
        transactionResponses.filter { !it.category.isIncome }.map { it.asExpense }
    }
}