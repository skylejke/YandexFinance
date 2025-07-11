package ru.point.serializable

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.dto.CategoryDto

@Serializable
data class Category(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("emoji") val emoji: String,
    @SerialName("isIncome") val isIncome: Boolean
)

val Category.asCategoryDto
    get() = CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )