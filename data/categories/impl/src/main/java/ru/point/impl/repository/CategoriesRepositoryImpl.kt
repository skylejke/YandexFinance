package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ru.point.api.repository.CategoriesRepository
import ru.point.database.dao.CategoriesDao
import ru.point.impl.model.asStateItemDto
import ru.point.impl.service.CategoriesService
import ru.point.serializable.asCategoryDto
import ru.point.utils.network.InternetTracker
import javax.inject.Inject


internal class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesService: CategoriesService,
    private val categoriesDao: CategoriesDao,
    private val internetTracker: InternetTracker
) : CategoriesRepository {

    override suspend fun getCategories() = withContext(Dispatchers.IO) {

        val cache = categoriesDao.getUsersCategories().first()

        if (internetTracker.online.first()) {
            val remote = categoriesService.getCategories()
                .map { resp -> (resp.expenseStats + resp.incomeStats).map { it.asStateItemDto } }
                .getOrThrow()

            categoriesDao.insertUsersCategories(remote)
            Result.success(remote)
        } else {
            Result.success(cache)
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean) = withContext(Dispatchers.IO) {
        val cache = categoriesDao.getCategoriesByType(isIncome).first()

        if (internetTracker.online.first()) {
            val remote = categoriesService.getCategoriesByType(isIncome)
                .map { list -> list.map { it.asCategoryDto } }
                .getOrThrow()

            categoriesDao.insertCategoriesByType(remote.map { it })
            Result.success(remote)
        } else {
            Result.success(cache.map { it })
        }
    }
}