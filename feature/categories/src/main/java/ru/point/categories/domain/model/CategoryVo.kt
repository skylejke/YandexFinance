package ru.point.categories.domain.model

import ru.point.api.model.StateItemDto

internal data class CategoryVo(
    val id: Int,
    val name: String,
    val emoji: String?,
)

internal val StateItemDto.asCategoryVo
    get() = CategoryVo(
        id = categoryId,
        name = categoryName,
        emoji = emoji
    )