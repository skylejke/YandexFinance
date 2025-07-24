package ru.point.account.ui.update.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.account.domain.model.AccountVo
import ru.point.utils.model.AppError

@Immutable
internal sealed interface UpdateAccountAction {
    data object LoadRequested : UpdateAccountAction
    data class LoadSuccess(val account: AccountVo) : UpdateAccountAction
    data class LoadError(val error: AppError) : UpdateAccountAction

    data class OnAccountNameEntered(val accountName: String): UpdateAccountAction

    data class OnAccountBalanceEntered(val accountBalance: String): UpdateAccountAction

    data class OnAccountCurrencyEntered(val accountCurrency: String): UpdateAccountAction

    data object OnUpdatePressed : UpdateAccountAction
}