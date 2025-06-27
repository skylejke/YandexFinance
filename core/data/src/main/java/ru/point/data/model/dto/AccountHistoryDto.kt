package ru.point.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountHistoryDto(
    @SerialName("id") val id: Int,
    @SerialName("accountId") val accountId: Int,
    @SerialName("changeType") val changeType: ChangeTypes,
    @SerialName("previousState") val previousState: AccountStateDto,
    @SerialName("newState") val newState: AccountStateDto,
    @SerialName("changeTimestamp") val changeTimestamp: String,
    @SerialName("createdAt") val createdAt: String
)

@Serializable
enum class ChangeTypes {
    CREATION,
    MODIFICATION
}