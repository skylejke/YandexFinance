package ru.point.yandexfinance.feature.account.ui.viewmodel

import ru.point.yandexfinance.feature.account.model.Account

sealed interface AccountAction {
    object LoadRequested : AccountAction
    data class LoadSuccess(val account: Account) : AccountAction
    data class LoadError(val message: String) : AccountAction
}
