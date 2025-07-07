package ru.point.account.ui.update.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.account.domain.usecase.GetAccountUseCase
import ru.point.account.domain.usecase.UpdateAccountUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class UpdateAccountViewModelFactory @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        UpdateAccountViewModel(
            getAccountUseCase = getAccountUseCase,
            updateAccountUseCase = updateAccountUseCase
        ) as T
}