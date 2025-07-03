package ru.point.account.ui.account.viewmodel

import ru.point.utils.model.AppError
import ru.point.account.domain.model.AccountVo

sealed interface AccountAction {
    data object LoadRequested : AccountAction
    data class LoadSuccess(val account: AccountVo) : AccountAction
    data class LoadError(val error: AppError) : AccountAction
}
