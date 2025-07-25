package ru.point.ui.scaffold.topappbar

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
data class TopAppBarState(
    @param:StringRes val titleRes: Int,
    val actions: List<TopAppBarAction> = emptyList(),
    val onBack: (() -> Unit)? = null,
)