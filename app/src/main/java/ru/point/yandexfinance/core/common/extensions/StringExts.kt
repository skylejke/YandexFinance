package ru.point.yandexfinance.core.common.extensions

fun String.startsWithEmoji(): Boolean = this.firstOrNull()?.isEmoji() == true

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