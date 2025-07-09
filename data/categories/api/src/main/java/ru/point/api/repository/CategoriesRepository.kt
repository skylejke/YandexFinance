package ru.point.api.repository

import ru.point.api.model.StateItemDto

interface CategoriesRepository {

    suspend fun getCategories(): Result<List<StateItemDto>>
}