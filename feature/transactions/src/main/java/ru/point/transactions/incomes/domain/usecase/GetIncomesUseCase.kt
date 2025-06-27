package ru.point.transactions.incomes.domain.usecase

import ru.point.transactions.incomes.domain.model.Income

interface GetIncomesUseCase {

    suspend operator fun invoke(): Result<List<Income>>
}