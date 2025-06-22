package ru.point.yandexfinance.feature.categories.ui.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.categories.model.Category

data class CategoriesState(
    val query: String = "",
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList<Category>(),
    val error: AppError? = null
)