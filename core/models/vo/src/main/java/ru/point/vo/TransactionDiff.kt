package ru.point.vo

import java.math.BigDecimal
import java.time.LocalDate

data class TransactionDiff(
    val date: LocalDate,
    val diff: BigDecimal,
) {

    val isPositive: Boolean get() = diff.signum() >= 0
}