package ru.point.utils.extensions

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
    val number = this.filter { it.isDigit() }.toIntOrNull() ?: 0
    return number.toFormattedCurrency(currency)
}

fun String.toAmountInt(): Int {
    val digitsOnly = replace("""\D""".toRegex(), "")
    return digitsOnly.toIntOrNull() ?: 0
}

fun String.toCurrencySymbol(): String =
    CurrencyParse.from(this)?.symbol ?: this

fun String.toTimeHHmm(): String {
    val instant = LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
    return instant.format(DateTimeFormatter.ofPattern("HH:mm"))
}