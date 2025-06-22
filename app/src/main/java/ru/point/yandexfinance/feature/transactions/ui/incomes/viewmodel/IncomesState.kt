package ru.point.yandexfinance.feature.transactions.ui.incomes.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.transactions.model.Income

data class IncomesState(
    val incomes: List<Income> = emptyList<Income>(),
    val isLoading: Boolean = true,
    val error: AppError? = null
)