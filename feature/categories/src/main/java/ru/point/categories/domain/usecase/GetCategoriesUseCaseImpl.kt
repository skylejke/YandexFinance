package ru.point.categories.domain.usecase

import ru.point.categories.domain.model.asCategoryVo
import ru.point.data.repository.categories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCaseImpl @Inject constructor(
    private val categoryRepository: CategoriesRepository
) : GetCategoriesUseCase {

    override suspend fun invoke() = categoryRepository.getCategories().map { categoryDtos ->
        categoryDtos.map { it.asCategoryVo }
    }
}