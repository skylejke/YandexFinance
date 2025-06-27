package ru.point.data.repository.categories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.data.service.CategoriesService
import javax.inject.Inject

/**
 * Репозиторий, отвечающий за получение категорий из удалённого источника.
 * Делегирует вызов [CategoriesService] и обеспечивает выполнение в IO-контексте.
 */
class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesService: CategoriesService
) : CategoriesRepository {

    override suspend fun getCategories() = withContext(Dispatchers.IO) {
        categoriesService.getCategories()
    }
}