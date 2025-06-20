package ru.point.yandexfinance.core.data.service

import retrofit2.http.GET
import ru.point.yandexfinance.core.data.model.CategoryDto

interface CategoriesService {

    @GET("api/v1/categories")
    suspend fun getCategories(): Result<List<CategoryDto>>


}