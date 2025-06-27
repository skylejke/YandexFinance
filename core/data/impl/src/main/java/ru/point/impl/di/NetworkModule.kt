package ru.point.impl.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import ru.point.impl.BuildConfig
import ru.point.impl.service.AccountService
import ru.point.impl.service.CategoriesService
import ru.point.impl.service.TransactionsService
import ru.point.utils.network.ApiKeyInterceptor
import ru.point.utils.network.RetryInterceptor
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .addInterceptor(RetryInterceptor())
            .build()

        @OptIn(ExperimentalSerializationApi::class)
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAccountService(retrofit: Retrofit) =
        retrofit.create<AccountService>()

    @Provides
    @Singleton
    internal fun provideCategoriesService(retrofit: Retrofit) =
        retrofit.create<CategoriesService>()

    @Provides
    @Singleton
    internal fun provideTransactionsService(retrofit: Retrofit) =
        retrofit.create<TransactionsService>()
}