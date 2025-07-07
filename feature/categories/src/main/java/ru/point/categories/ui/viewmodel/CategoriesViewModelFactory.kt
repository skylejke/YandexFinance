package ru.point.categories.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.categories.domain.usecase.GetCategoriesUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class CategoriesViewModelFactory @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        CategoriesViewModel(getCategoriesUseCase = getCategoriesUseCase) as T
}