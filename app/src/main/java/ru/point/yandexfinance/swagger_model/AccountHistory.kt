package ru.point.yandexfinance.swagger_model

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: ChangeTypes,
    val previousState: AccountState,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)

enum class ChangeTypes {
    CREATION,
    MODIFICATION
}