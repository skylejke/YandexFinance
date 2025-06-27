package ru.point.yandexfinance.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.point.account.ui.viewmodel.AccountViewModel
import ru.point.categories.ui.viewmodel.CategoriesViewModel
import ru.point.transactions.expenses.ui.viewmodel.ExpensesViewModel
import ru.point.transactions.history.ui.viewmodel.TransactionHistoryViewModel
import ru.point.transactions.incomes.ui.viewmodel.IncomesViewModel
import ru.point.yandexfinance.di.utils.DaggerViewModelFactory
import ru.point.yandexfinance.di.utils.ViewModelKey

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    abstract fun bindAccountViewModel(accountViewModel: AccountViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExpensesViewModel::class)
    abstract fun bindExpensesViewModel(expensesViewModel: ExpensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(IncomesViewModel::class)
    abstract fun bindIncomesViewModel(incomesViewModel: IncomesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TransactionHistoryViewModel::class)
    abstract fun bindTransactionHistoryViewModel(transactionHistoryViewModel: TransactionHistoryViewModel): ViewModel
}