package ru.point.utils.extensions

import java.text.DecimalFormat

val currencyFormatter: DecimalFormat by lazy {
    DecimalFormat("#,###.##").apply {
        decimalFormatSymbols = decimalFormatSymbols.apply {
            groupingSeparator = ' ' // пробел как разделитель тысяч
            decimalSeparator = '.' // точка как десятичный
        }
        minimumFractionDigits = 0
        maximumFractionDigits = 2
        isGroupingUsed = true
    }
}


fun Number.toFormattedCurrency(currency: String): String {
    return "${currencyFormatter.format(this)} ${currency.toCurrencySymbol()}"
}