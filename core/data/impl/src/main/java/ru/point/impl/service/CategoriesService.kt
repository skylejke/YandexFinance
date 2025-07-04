package ru.point.impl.service

import retrofit2.http.GET
import retrofit2.http.Path
import ru.point.impl.BuildConfig
import ru.point.impl.model.AccountResponse

internal interface CategoriesService {

    @GET("api/v1/accounts/{accountId}")
    suspend fun getCategories(
        @Path("accountId") accountId: Int = BuildConfig.ACCOUNT_ID.toInt(),
    ): Result<AccountResponse>
}