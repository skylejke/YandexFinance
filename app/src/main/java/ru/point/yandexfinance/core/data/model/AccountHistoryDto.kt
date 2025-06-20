package ru.point.yandexfinance.core.data.model

data class AccountHistoryDto(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeTypes,
    val previousState: AccountStateDto,
    val newState: AccountStateDto,
    val changeTimestamp: String,
    val createdAt: String
)

enum class ChangeTypes {
    CREATION,
    MODIFICATION
}