package ru.point.categories.ui.viewmodel

import ru.point.categories.domain.model.CategoryVo
import ru.point.utils.model.AppError

internal data class CategoriesState(
    val query: String = "",
    val isLoading: Boolean = true,
    val categories: List<CategoryVo> = emptyList(),
    val error: AppError? = null
)