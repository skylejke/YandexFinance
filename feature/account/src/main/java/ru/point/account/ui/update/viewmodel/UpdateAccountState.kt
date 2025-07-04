package ru.point.account.ui.update.viewmodel

import ru.point.utils.model.AppError

data class UpdateAccountState(
    val isLoading: Boolean = true,
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
    val error: AppError? = null
)
