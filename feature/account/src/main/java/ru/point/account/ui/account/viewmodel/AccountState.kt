package ru.point.account.ui.account.viewmodel

import ru.point.utils.model.AppError
import ru.point.account.domain.model.AccountVo

data class AccountState(
    val isLoading: Boolean = true,
    val account: AccountVo? = null,
    val error: AppError? = null
)
