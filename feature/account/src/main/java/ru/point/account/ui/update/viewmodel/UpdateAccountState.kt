package ru.point.account.ui.update.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.model.AppError

@Immutable
internal data class UpdateAccountState(
    val isLoading: Boolean = true,
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
    val error: AppError? = null
)
