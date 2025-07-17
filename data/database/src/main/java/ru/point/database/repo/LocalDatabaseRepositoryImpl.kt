package ru.point.database.repo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ru.point.api.repository.AccountRepository
import ru.point.api.repository.CategoriesRepository
import ru.point.api.repository.TransactionsRepository
import ru.point.database.dao.AccountDao
import ru.point.database.dao.CategoriesDao
import ru.point.database.dao.TransactionsDao
import ru.point.utils.extensions.endOfDayIso
import ru.point.utils.extensions.startOfDayIso
import ru.point.utils.network.InternetTracker
import java.time.LocalDate

internal class LocalDatabaseRepositoryImpl(
    private val internetTracker: InternetTracker,
    private val transactionsDao: TransactionsDao,
    private val categoriesDao: CategoriesDao,
    private val accountDao: AccountDao,
    private val transactionsRepository: TransactionsRepository,
    private val accountRepository: AccountRepository,
    private val categoriesRepository: CategoriesRepository,
) : LocalDatabaseRepository {

    override suspend fun prefetchTransactions() =
        withContext(Dispatchers.IO) {
            if (internetTracker.online.first()) {
                val result = transactionsRepository
                    .getTransactionsForPeriod(
                        startDate = getYearStart(),
                        endDate = getTodayDate()
                    )
                result.onSuccess { list ->
                    transactionsDao.clearTransactionsTable()
                    transactionsDao.insertAllTransactions(list)
                }
                result
            } else {
                val cached = transactionsDao.getTransactionsInPeriod(
                    getYearStart().startOfDayIso(),
                    getTodayDate().endOfDayIso()
                ).first()
                Result.success(cached)
            }
        }

    override suspend fun prefetchAccounts() =
        withContext(Dispatchers.IO) {
            if (internetTracker.online.first()) {
                val result = accountRepository.getAccounts()
                result.onSuccess { list ->
                    accountDao.clearAccountsTable()
                    accountDao.insertAllAccounts(list)
                }
                result
            } else {
                val cached = accountDao.getAllAccounts().first()
                Result.success(cached)
            }
        }

    override suspend fun prefetchUsersCategories() =
        withContext(Dispatchers.IO) {
            if (internetTracker.online.first()) {
                val result = categoriesRepository.getCategories()
                result.onSuccess { list ->
                    categoriesDao.clearUsersCategories()
                    categoriesDao.insertUsersCategories(list)
                }
                result
            } else {
                val cached = categoriesDao.getUsersCategories().first()
                Result.success(cached)
            }
        }

    override suspend fun prefetchCategoriesByType(isIncome: Boolean) =
        withContext(Dispatchers.IO) {
            if (internetTracker.online.first()) {
                val result = categoriesRepository.getCategoriesByType(isIncome)
                result.onSuccess { list ->
                    categoriesDao.clearCategoriesByType(isIncome)
                    categoriesDao.insertCategoriesByType(list)
                }
                result
            } else {
                val cached = categoriesDao.getCategoriesByType(isIncome).first()
                Result.success(cached)
            }
        }

    private fun getYearStart() =
        LocalDate.now()
            .withDayOfYear(1)
            .format(java.time.format.DateTimeFormatter.ISO_DATE)

    private fun getTodayDate() =
        LocalDate.now()
            .format(java.time.format.DateTimeFormatter.ISO_DATE)
}