package ru.point.categories.di.component

import dagger.Component
import ru.point.categories.di.deps.CategoriesDeps
import ru.point.categories.ui.viewmodel.CategoriesViewModelFactory
import ru.point.utils.di.FeatureScope

@[FeatureScope Component(dependencies = [CategoriesDeps::class])]
internal interface CategoriesComponent {

    @Component.Builder
    interface Builder {
        fun deps(categoriesDeps: CategoriesDeps): Builder
        fun build(): CategoriesComponent
    }

    val categoriesViewModelFactory: CategoriesViewModelFactory
}