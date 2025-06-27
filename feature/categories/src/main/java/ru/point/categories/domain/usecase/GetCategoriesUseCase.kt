package ru.point.categories.domain.usecase

import ru.point.categories.domain.model.CategoryVo

interface GetCategoriesUseCase {

    suspend operator fun invoke(): Result<List<CategoryVo>>
}