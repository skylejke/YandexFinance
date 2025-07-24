package ru.point.categories.ui.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.model.AppError
import ru.point.vo.CategoryVo

@Immutable
internal sealed interface CategoriesAction {
    data object LoadRequested : CategoriesAction
    data class LoadSuccess(val categories: List<CategoryVo>) : CategoriesAction
    data class LoadError(val error: AppError) : CategoriesAction
    data class SearchQueryChanged(val query: String) : CategoriesAction

    data class FilterCategories(val filterCategories: List<CategoryVo>) : CategoriesAction
}