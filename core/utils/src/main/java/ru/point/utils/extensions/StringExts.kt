package ru.point.utils.extensions

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

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

fun String.sanitizeDecimalInput(): String {
    if (isEmpty()) return ""

    val normalized = replace(',', '.')
    val cleaned = normalized.extractDecimalDigits()
    return cleaned.normalizeLeadingZeros()
}

private fun String.extractDecimalDigits(): String {
    val sb = StringBuilder()
    var dotFound = false
    var decimals = 0

    for (ch in this) {
        when {
            ch.isDigit() -> {
                if (!dotFound) sb.append(ch)
                else if (decimals < 2) {
                    sb.append(ch)
                    decimals++
                }
            }

            ch == '.' && !dotFound -> {
                dotFound = true
                if (sb.isEmpty()) sb.append('0')
                sb.append('.')
            }
        }
    }

    return sb.toString()
}

private fun String.normalizeLeadingZeros(): String {
    val parts = split('.', limit = 2)
    val intPart = parts[0].trimStart('0').ifEmpty { "0" }

    return if (parts.size == 2) {
        "$intPart.${parts[1]}"
    } else {
        intPart
    }
}

fun String.formatAsDate(): String {
    return ZonedDateTime.parse(this)
        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
}

fun String.formatAsTime(): String {
    return ZonedDateTime.parse(this)
        .format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun String.toIsoDate(): String {
    val inputFormatter = fullDateFormatter
    val outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    return LocalDate.parse(this, inputFormatter).format(outputFormatter)
}

fun String.toEpochMillis(): Long =
    LocalDate.parse(this, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()


fun String.startOfDayIso() = "${this}T00:00:00.000Z"

fun String.endOfDayIso() = "${this}T23:59:59.999Z"

fun String.toReadableDateTimeWithSeconds(): String {
    return try {
        val parsed = ZonedDateTime.parse(this)
        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy, HH:mm:ss", Locale.forLanguageTag("ru"))
        parsed.format(formatter)
    } catch (_: Exception) {
        this
    }
}

fun String.toBigDecimalPercent(): BigDecimal {
    val cleaned = this.replace("[%\\s]".toRegex(), "")
    return cleaned.toBigDecimalOrNull() ?: BigDecimal.ZERO
}

fun String.toBigDecimalAmount(): BigDecimal {
    val cleaned = this.replace("[^\\d.,]".toRegex(), "")
        .replace(',', '.')
    return cleaned.toBigDecimalOrNull() ?: BigDecimal.ZERO
}