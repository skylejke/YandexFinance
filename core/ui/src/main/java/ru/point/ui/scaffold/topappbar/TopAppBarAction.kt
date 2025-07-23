package ru.point.ui.scaffold.topappbar

import androidx.annotation.DrawableRes

data class TopAppBarAction(
    @param:DrawableRes val iconResId: Int,
    val action: () -> Unit,
    val tag: String = ""
)