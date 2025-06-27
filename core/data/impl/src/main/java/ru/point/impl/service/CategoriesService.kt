package ru.point.impl.service

import retrofit2.http.GET
import ru.point.impl.model.Category

internal interface CategoriesService {

    @GET("api/v1/categories") // Знаю, что тут ручка не та, потом поправлю
    suspend fun getCategories(): Result<List<Category>>
}