package ru.point.yandexfinance.feature.categories.ui.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.categories.model.Category

sealed interface CategoriesAction {
    data object LoadRequested : CategoriesAction
    data class LoadSuccess(val categories: List<Category>) : CategoriesAction
    data class LoadError(val error: AppError) : CategoriesAction
    data class SearchQueryChanged(val query: String) : CategoriesAction
}