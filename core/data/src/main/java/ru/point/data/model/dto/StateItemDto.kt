package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StateItemDto(
    @SerialName("categoryIDN") val categoryIDN: Int,
    @SerialName("categoryName") val categoryName: String,
    @SerialName("emoji") val emoji: String,
    @SerialName("amount") val amount: String
)
