package ru.point.account.ui.account.viewmodel

import ru.point.account.domain.model.AccountVo
import ru.point.utils.model.AppError

internal data class AccountState(
    val isLoading: Boolean = true,
    val account: AccountVo? = null,
    val error: AppError? = null
)
