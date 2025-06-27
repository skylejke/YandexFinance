package ru.point.yandexfinance.di.modules

import dagger.Binds
import dagger.Module
import ru.point.account.domain.usecase.GetAccountUseCase
import ru.point.account.domain.usecase.GetAccountUseCaseImpl
import ru.point.categories.domain.usecase.GetCategoriesUseCase
import ru.point.categories.domain.usecase.GetCategoriesUseCaseImpl
import ru.point.transactions.expenses.domain.usecase.GetExpensesUseCase
import ru.point.transactions.expenses.domain.usecase.GetExpensesUseCaseImpl
import ru.point.transactions.history.domain.usecase.GetTransactionsHistoryUseCase
import ru.point.transactions.history.domain.usecase.GetTransactionsHistoryUseCaseImpl
import ru.point.transactions.incomes.domain.usecase.GetIncomesUseCase
import ru.point.transactions.incomes.domain.usecase.GetIncomesUseCaseImpl

@Module
interface DomainModule {

    @Binds
    fun bindGetAccountUseCase(getAccountUseCaseImpl: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    fun bindGetCategoriesUseCase(getCategoriesUseCaseImpl: GetCategoriesUseCaseImpl): GetCategoriesUseCase

    @Binds
    fun bindGetExpensesUseCase(getExpensesUseCaseImpl: GetExpensesUseCaseImpl): GetExpensesUseCase

    @Binds
    fun bindGetIncomesUseCase(getIncomesUseCaseImpl: GetIncomesUseCaseImpl): GetIncomesUseCase

    @Binds
    fun bindGetTransactionsHistoryUseCase(
        getTransactionsHistoryUseCaseImpl: GetTransactionsHistoryUseCaseImpl
    ): GetTransactionsHistoryUseCase
}