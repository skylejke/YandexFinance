package ru.point.transactions.domain.usecase

import ru.point.api.repository.CategoriesRepository
import ru.point.dto.asCategoryVo
import javax.inject.Inject

internal class GetCategoriesByTypeUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {

    suspend operator fun invoke(isIncome: Boolean) =
        categoriesRepository.getCategoriesByType(isIncome).map { categoryDtos ->
            categoryDtos.map { it.asCategoryVo }
        }
}