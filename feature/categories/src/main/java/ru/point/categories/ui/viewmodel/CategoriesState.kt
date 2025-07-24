package ru.point.categories.ui.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.model.AppError
import ru.point.vo.CategoryVo

@Immutable
internal data class CategoriesState(
    val query: String = "",
    val isLoading: Boolean = true,
    val categories: List<CategoryVo> = emptyList(),
    val error: AppError? = null
)