package ru.point.categories.domain.usecase

import ru.point.api.repository.CategoriesRepository
import ru.point.categories.domain.model.CategoryVo
import ru.point.categories.domain.model.asCategoryVo
import javax.inject.Inject

internal class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoriesRepository
) {

    suspend operator fun invoke(query: String = ""): Result<List<CategoryVo>> {
        val filteredCategories = categoryRepository.getCategories().map { stateItemDtos ->
            if (query.isBlank()) {
                stateItemDtos
            } else {
                val handledQuery = query.trim().lowercase()
                stateItemDtos.filter { it.categoryName.lowercase().contains(handledQuery) }
            }
        }
        return filteredCategories.map { stateItemDtos ->
            stateItemDtos.map { it.asCategoryVo }
        }
    }
}

