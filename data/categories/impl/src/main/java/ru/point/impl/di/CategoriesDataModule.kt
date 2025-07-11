package ru.point.impl.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.point.api.repository.CategoriesRepository
import ru.point.impl.repository.CategoriesRepositoryImpl
import ru.point.impl.service.CategoriesService
import javax.inject.Singleton

@Module
class CategoriesDataModule {

    @[Provides Singleton]
    internal fun provideCategoriesService(retrofit: Retrofit) =
        retrofit.create<CategoriesService>()

    @[Provides Singleton]
    internal fun provideCategoriesRepository(categoriesService: CategoriesService): CategoriesRepository =
        CategoriesRepositoryImpl(categoriesService = categoriesService)
}