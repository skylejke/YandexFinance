package ru.point.yandexfinance.feature.account.ui.viewmodel

import ru.point.yandexfinance.feature.account.model.Account

data class AccountState(
    val account: Account? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
