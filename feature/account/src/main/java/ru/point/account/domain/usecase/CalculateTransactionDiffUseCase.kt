package ru.point.account.domain.usecase

import ru.point.api.model.TransactionResponseDto
import ru.point.api.repository.TransactionsRepository
import ru.point.utils.extensions.isoDateFormatter
import ru.point.vo.TransactionDiff
import java.time.LocalDate
import javax.inject.Inject

class CalculateTransactionDiffUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) {

    suspend operator fun invoke(
        startDate: String = LocalDate.now().minusMonths(1).format(isoDateFormatter),
        endDate: String = LocalDate.now().format(isoDateFormatter),
    ) = transactionsRepository.getTransactionsForPeriod(
        startDate = startDate,
        endDate = endDate
    ).map { transactions ->
        val perDay: Map<LocalDate, List<TransactionResponseDto>> = transactions
            .groupBy { LocalDate.parse(it.transactionDate.take(10)) }

        val allDays: List<LocalDate> = (0..29)
            .map { LocalDate.now().minusDays(it.toLong()) }
            .reversed()

        allDays.map { day ->
            val dayTransactions = perDay[day].orEmpty()

            val incomes = dayTransactions
                .filter { it.category.isIncome }
                .sumOf { it.amount.toBigDecimal() }

            val expenses = dayTransactions
                .filter { !it.category.isIncome }
                .sumOf { it.amount.toBigDecimal() }

            val diff = incomes - expenses

            TransactionDiff(date = day, diff = diff)
        }
    }
}