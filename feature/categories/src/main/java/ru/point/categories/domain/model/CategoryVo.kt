package ru.point.categories.domain.model

import ru.point.data.model.dto.CategoryDto

data class CategoryVo(
    val id: Int,
    val name: String,
    val emoji: String?,
)

val CategoryDto.asCategoryVo
    get() = CategoryVo(
        id = id,
        name = name,
        emoji = emoji
    )