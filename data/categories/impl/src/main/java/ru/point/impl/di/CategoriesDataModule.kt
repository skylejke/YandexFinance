package ru.point.impl.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.point.api.repository.CategoriesRepository
import ru.point.database.dao.CategoriesDao
import ru.point.impl.repository.CategoriesRepositoryImpl
import ru.point.impl.service.CategoriesService
import ru.point.utils.network.InternetTracker
import javax.inject.Singleton

@Module
class CategoriesDataModule {

    @[Provides Singleton]
    internal fun provideCategoriesService(retrofit: Retrofit) =
        retrofit.create<CategoriesService>()

    @[Provides Singleton]
    internal fun provideCategoriesRepository(
        categoriesService: CategoriesService,
        categoriesDao: CategoriesDao,
        internetTracker: InternetTracker
    ): CategoriesRepository =
        CategoriesRepositoryImpl(
            categoriesService = categoriesService,
            categoriesDao = categoriesDao,
            internetTracker = internetTracker,
        )
}