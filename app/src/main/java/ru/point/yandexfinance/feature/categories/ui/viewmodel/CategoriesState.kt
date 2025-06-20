package ru.point.yandexfinance.feature.categories.ui.viewmodel

import ru.point.yandexfinance.feature.categories.model.Category

data class CategoriesState(
    val query: String = "",
    val isLoading: Boolean = false,
    val categories: List<Category> = emptyList<Category>(),
    val error: String? = null
)