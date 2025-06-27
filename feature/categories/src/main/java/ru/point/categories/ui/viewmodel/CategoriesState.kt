package ru.point.categories.ui.viewmodel

import ru.point.utils.model.AppError
import ru.point.categories.domain.model.CategoryVo

data class CategoriesState(
    val query: String = "",
    val isLoading: Boolean = true,
    val categories: List<CategoryVo> = emptyList<CategoryVo>(),
    val error: AppError? = null
)