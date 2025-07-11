package ru.point.api.model

data class AccountStateDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)