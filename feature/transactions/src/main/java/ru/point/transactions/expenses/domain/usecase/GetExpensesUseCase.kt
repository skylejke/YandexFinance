package ru.point.transactions.expenses.domain.usecase

import ru.point.transactions.expenses.domain.model.Expense

interface GetExpensesUseCase {

    suspend operator fun invoke(): Result<List<Expense>>
}