package ru.point.api.repository

import ru.point.api.model.CategoryDto

interface CategoriesRepository {

    suspend fun getCategories(): Result<List<CategoryDto>>
}