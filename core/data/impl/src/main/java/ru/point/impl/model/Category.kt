package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.CategoryDto

@Serializable
internal data class Category(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("emoji") val emoji: String,
    @SerialName("isIncome") val isIncome: Boolean
)

internal val Category.asCategoryDto
    get() = CategoryDto(
        id = id,
        name = name,
        emoji = emoji,
        isIncome = isIncome
    )