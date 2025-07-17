package ru.point.transactions.domain.usecase

import ru.point.api.model.TransactionResponseDto
import ru.point.api.repository.TransactionsRepository
import ru.point.dto.CategoryDto
import ru.point.transactions.domain.model.AnalysisCategories
import ru.point.utils.extensions.toAmountInt
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

internal class GetAnalysisTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke(
        isIncome: Boolean,
        startDate: String = LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ISO_DATE),
        endDate: String = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    ): Result<List<AnalysisCategories>> {
        val transactionResponseDtos = transactionsRepository
            .getTransactionsForPeriod(startDate = startDate, endDate = endDate)
            .getOrThrow()
            .filter { it.category.isIncome == isIncome }

        val totalAmount = transactionResponseDtos.sumOf { it.amount.toAmountInt() }

        val byCategory: Map<CategoryDto, List<TransactionResponseDto>> =
            transactionResponseDtos.groupBy { it.category }

        val analysisCategories = byCategory.map { (category, list) ->
            val sum = list.sumOf { it.amount.toAmountInt() }
            val percent = if (totalAmount > 0) sum * 100f / totalAmount else 0f
            val part = String.format(Locale.getDefault(), "%.2f %%", percent)
            AnalysisCategories(
                id = category.id,
                title = category.name,
                emojiIcon = category.emoji,
                amount = sum.toString(),
                currency = list.first().account.currency,
                part = part
            )
        }.sortedByDescending { it.amount.toAmountInt() }

        return Result.success(analysisCategories)
    }
}
