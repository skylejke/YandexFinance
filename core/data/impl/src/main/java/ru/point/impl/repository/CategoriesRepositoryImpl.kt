package ru.point.impl.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.api.repository.CategoriesRepository
import ru.point.impl.model.asStateItemDto
import ru.point.impl.service.CategoriesService
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение категорий из удалённого источника.
 * Делегирует вызов [CategoriesService] и обеспечивает выполнение в IO-контексте.
 */
internal class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesService: CategoriesService
) : CategoriesRepository {

    override suspend fun getCategories(query: String) = withContext(Dispatchers.IO) {
        categoriesService.getCategories().map { account ->
            (account.expenseStats + account.incomeStats).map { it.asStateItemDto }
        }.map { allCategories ->
            if (query.isBlank()) {
                allCategories
            } else {
                withContext(Dispatchers.Default) {
                    val handledQuery = query.trim().lowercase()
                    allCategories.filter { it.categoryName.lowercase().contains(handledQuery) }
                }
            }
        }
    }
}