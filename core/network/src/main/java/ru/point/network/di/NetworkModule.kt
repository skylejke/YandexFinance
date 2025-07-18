package ru.point.network.di

import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.point.network.BuildConfig
import ru.point.network.utils.ApiKeyInterceptor
import ru.point.network.utils.RetryInterceptor
import ru.point.utils.network.InternetTracker
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
    internal fun provideConnectivityManager(
        context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    internal fun provideInternetTracker(
        connectivityManager: ConnectivityManager
    ) = InternetTracker(connectivityManager = connectivityManager)
}