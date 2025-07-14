package ru.point.transactions.domain.usecase

import ru.point.api.repository.TransactionsRepository
import ru.point.transactions.domain.model.AnalysisTransaction
import ru.point.transactions.domain.model.asAnalysisTransaction
import ru.point.utils.extensions.toAmountInt
import java.time.Instant
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
    ): Result<List<AnalysisTransaction>> {

        val filteredSortedDtos = transactionsRepository
            .getTransactionsForPeriod(startDate = startDate, endDate = endDate)
            .getOrThrow()
            .filter { it.category.isIncome == isIncome }
            .sortedByDescending { Instant.parse(it.transactionDate) }

        val totalAmount = filteredSortedDtos
            .sumOf { dto -> dto.amount.toAmountInt() }

        return Result.success(
            filteredSortedDtos.map { dto ->
                val thisAmount = dto.amount.toAmountInt()
                val percent = if (totalAmount > 0) {
                    (thisAmount * 100f / totalAmount)
                } else 0f
                val part = String.format(Locale.getDefault(), "%.2f %%", percent)
                dto.asAnalysisTransaction(part = part)
            }
        )

// вариант с целыми числами
//        return Result.success(
//            filteredSortedDtos.map { dto ->
//                val thisAmount = dto.amount.toAmountInt()
//                val pct = if (totalAmount > 0) {
//                    val rawPercent = thisAmount * 100f / totalAmount
//                    if (rawPercent < 1f && thisAmount > 0) 1 else rawPercent.roundToInt()
//                } else 0
//                dto.asAnalysisTransaction(part = "$pct %")
//            }
//        )
    }
}