package ru.point.impl.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.point.api.model.StateItemDto

@Serializable
internal data class StateItem(
    @SerialName("categoryId") val categoryId: Int,
    @SerialName("categoryName") val categoryName: String,
    @SerialName("emoji") val emoji: String,
    @SerialName("amount") val amount: String,
)

internal val StateItem.asStateItemDto
    get() = StateItemDto(
        categoryId = categoryId,
        categoryName = categoryName,
        emoji = emoji,
        amount = amount
    )