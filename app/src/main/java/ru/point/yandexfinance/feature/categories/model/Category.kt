package ru.point.yandexfinance.feature.categories.model

import ru.point.yandexfinance.core.data.model.CategoryDto

data class Category(
    val id: Int,
    val name: String,
    val emoji: String?,
)

val CategoryDto.toDomain
    get() = Category(
        id = id,
        name = name,
        emoji = emoji
    )