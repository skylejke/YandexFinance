package ru.point.yandexfinance.feature.account.ui.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.account.model.Account

sealed interface AccountAction {
    data object LoadRequested : AccountAction
    data class LoadSuccess(val account: Account) : AccountAction
    data class LoadError(val error: AppError) : AccountAction
}
