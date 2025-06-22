package ru.point.yandexfinance.feature.account.ui.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.account.model.Account

data class AccountState(
    val isLoading: Boolean = true,
    val account: Account? = null,
    val error: AppError? = null
)
