package ru.point.dto

import ru.point.vo.CategoryVo

data class CategoryDto(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean
)

val CategoryDto.asCategoryVo
    get() = CategoryVo(
        id = id,
        name = name,
        emoji = emoji
    )