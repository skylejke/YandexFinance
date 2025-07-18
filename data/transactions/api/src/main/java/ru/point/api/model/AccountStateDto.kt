package ru.point.api.model

data class AccountStateDto(
    val id: Int = -1,
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
)