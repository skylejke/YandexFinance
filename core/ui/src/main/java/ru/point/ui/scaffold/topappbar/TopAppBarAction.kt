package ru.point.ui.scaffold.topappbar

data class TopAppBarAction(
    val iconResId: Int,
    val action: () -> Unit,
    val tag: String = ""
)