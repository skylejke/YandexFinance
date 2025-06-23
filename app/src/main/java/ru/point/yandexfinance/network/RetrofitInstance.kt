package ru.point.yandexfinance.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import ru.point.yandexfinance.BuildConfig
import ru.point.yandexfinance.core.data.service.AccountService
import ru.point.yandexfinance.core.data.service.CategoriesService
import ru.point.yandexfinance.core.data.service.TransactionsService

object RetrofitInstance {

    private val json = Json { ignoreUnknownKeys = true }
    private val contentType = "application/json".toMediaType()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .addInterceptor(RetryInterceptor())
        .build()

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    val categoriesApi by lazy {
        retrofit.create<CategoriesService>()
    }

    val accountApi by lazy {
        retrofit.create<AccountService>()
    }

    val transactionsApi by lazy {
        retrofit.create<TransactionsService>()
    }
}