package ru.point.transactions.ui.editor.viewmodel.state

import androidx.compose.runtime.Immutable
import ru.point.utils.model.AppError
import ru.point.vo.CategoryVo

@Immutable
internal data class CategoriesState(
    val categoriesList: List<CategoryVo> = emptyList(),
    val categoriesLoadError: AppError? = null,
    val isCategoriesLoading: Boolean = false,
)