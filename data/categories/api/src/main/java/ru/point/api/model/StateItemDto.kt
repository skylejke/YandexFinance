package ru.point.api.model

import ru.point.vo.CategoryVo

data class StateItemDto(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String,
)

val StateItemDto.asCategoryVo
    get() = CategoryVo(
        id = categoryId,
        name = categoryName,
        emoji = emoji
    )