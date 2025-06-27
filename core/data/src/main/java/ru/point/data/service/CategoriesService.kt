package ru.point.data.service

import retrofit2.http.GET
import ru.point.data.model.dto.CategoryDto

interface CategoriesService {

    @GET("api/v1/categories")
    suspend fun getCategories(): Result<List<CategoryDto>>
}