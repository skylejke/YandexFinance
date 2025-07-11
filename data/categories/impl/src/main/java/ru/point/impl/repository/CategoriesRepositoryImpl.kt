package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.api.repository.CategoriesRepository
import ru.point.impl.model.asStateItemDto
import ru.point.impl.service.CategoriesService
import ru.point.serializable.asCategoryDto
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение категорий из удалённого источника.
 * Делегирует вызов [CategoriesService] и обеспечивает выполнение в IO-контексте.
 */
internal class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesService: CategoriesService
) : CategoriesRepository {

    override suspend fun getCategories() = withContext(Dispatchers.IO) {
        categoriesService.getCategories().map { account ->
            (account.expenseStats + account.incomeStats).map { it.asStateItemDto }
        }
    }

    override suspend fun getCategoriesByType(isIncome: Boolean) = withContext(Dispatchers.IO) {
        categoriesService.getCategoriesByType(isIncome = isIncome).map { categories ->
            categories.map { it.asCategoryDto }
        }
    }
}