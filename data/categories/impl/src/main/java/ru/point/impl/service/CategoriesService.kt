package ru.point.impl.service

import retrofit2.http.GET
import retrofit2.http.Path
import ru.point.core.utils.BuildConfig
import ru.point.impl.model.AccountResponse
import ru.point.serializable.Category

internal interface CategoriesService {

    @GET("api/v1/accounts/{accountId}")
    suspend fun getCategories(
        @Path("accountId") accountId: Int = BuildConfig.ACCOUNT_ID.toInt(),
    ): Result<AccountResponse>

    @GET("api/v1/categories/type/{isIncome}")
    suspend fun getCategoriesByType(
        @Path("isIncome") isIncome: Boolean
    ): Result<List<Category>>
}