package ru.point.data.repository.categories

import ru.point.data.model.dto.CategoryDto

interface CategoriesRepository {

    suspend fun getCategories(): Result<List<CategoryDto>>
}