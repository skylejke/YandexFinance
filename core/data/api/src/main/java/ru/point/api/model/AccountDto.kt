package ru.point.api.model

data class AccountDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)