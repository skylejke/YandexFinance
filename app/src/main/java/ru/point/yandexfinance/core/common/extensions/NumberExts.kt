package ru.point.yandexfinance.core.common.extensions

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

val currencyFormatter: DecimalFormat by lazy {
    DecimalFormat(
        "#,###",
        DecimalFormatSymbols.getInstance().apply {
            groupingSeparator = '\u00A0'
        }
    )
}

fun Int.toFormattedCurrency(currency: String): String =
    "${currencyFormatter.format(this)} $currency"