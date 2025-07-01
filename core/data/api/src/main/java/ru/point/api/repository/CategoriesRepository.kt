package ru.point.api.repository

import ru.point.api.model.StateItemDto

interface CategoriesRepository {

    suspend fun getCategories(query: String): Result<List<StateItemDto>>
}