package ru.point.utils.extensions

import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.startsWithEmoji(): Boolean = firstOrNull()?.isEmoji() == true

fun String.initials(count: Int = 2): String {
    return this
        .split("\\s+".toRegex())
        .filter { it.isNotBlank() }
        .take(count)
        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
        .joinToString("")
}

fun String.toFormattedCurrency(currency: String): String {
    val numeric = this.filter { it.isDigit() || it == '.' }
    val number = numeric.toBigDecimalOrNull() ?: BigDecimal.ZERO
    return number.toFormattedCurrency(currency)
}

fun String.toAmountInt(): Int =
    this.substringBefore('.').toIntOrNull() ?: 0

fun String.toCurrencySymbol() =
    CurrencyParse.fromCode(this)?.symbol ?: this

fun String.toCurrencyCode() =
    CurrencyParse.toCode(this)?.code ?: this

fun String.toTimeHHmm(): String {
    val instant = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    return instant.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun String.extractNumericBalance() = filter { it.isDigit() || it == '.' }
