package ru.point.yandexfinance.feature.categories.ui.viewmodel

import ru.point.yandexfinance.feature.categories.model.Category

sealed interface CategoriesAction {
    data object LoadRequested : CategoriesAction
    data class LoadCategories(val categories: List<Category>) : CategoriesAction
    data class QueryChanged(val query: String) : CategoriesAction
    data class Error(val message: String) : CategoriesAction
}