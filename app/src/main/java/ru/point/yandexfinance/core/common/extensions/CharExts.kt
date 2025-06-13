package ru.point.yandexfinance.core.common.extensions

fun Char.isEmoji(): Boolean {
    val type = Character.getType(this)
    return type == Character.OTHER_SYMBOL.toInt() || type == Character.SURROGATE.toInt()
}