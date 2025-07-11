package ru.point.api.repository

import ru.point.api.model.StateItemDto
import ru.point.dto.CategoryDto

interface CategoriesRepository {

    suspend fun getCategories(): Result<List<StateItemDto>>

    suspend fun getCategoriesByType(isIncome: Boolean): Result<List<CategoryDto>>
}