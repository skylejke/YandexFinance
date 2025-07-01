package ru.point.categories.domain.usecase

import ru.point.api.repository.CategoriesRepository
import ru.point.categories.domain.model.asCategoryVo
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoriesRepository
) {

    suspend operator fun invoke(query: String = "") = categoryRepository.getCategories(query).map { stateItemDtos ->
        stateItemDtos.map { it.asCategoryVo }
    }
}