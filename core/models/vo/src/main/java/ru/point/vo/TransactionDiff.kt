package ru.point.vo

import androidx.compose.runtime.Immutable
import java.math.BigDecimal
import java.time.LocalDate

@Immutable
data class TransactionDiff(
    val date: LocalDate,
    val diff: BigDecimal,
) {

    val isPositive: Boolean get() = diff.signum() >= 0
}