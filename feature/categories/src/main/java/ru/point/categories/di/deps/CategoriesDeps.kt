package ru.point.categories.di.deps

import ru.point.api.repository.CategoriesRepository

interface CategoriesDeps {

    val categoriesRepository: CategoriesRepository
}