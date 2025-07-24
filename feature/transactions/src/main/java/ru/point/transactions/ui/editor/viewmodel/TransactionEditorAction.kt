package ru.point.transactions.ui.editor.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.transactions.domain.model.AccountData
import ru.point.transactions.domain.model.TransactionVo
import ru.point.utils.model.AppError
import ru.point.vo.CategoryVo

@Immutable
internal sealed interface TransactionEditorAction {

    sealed interface LastTimeSync : TransactionEditorAction {

        data class OnGetLastTimeSync(val lastTimeSync: String) : LastTimeSync
    }

    sealed interface Initial : TransactionEditorAction {

        data object InitialLoadRequested : Initial

        data class InitialLoadError(val error: AppError) : Initial

        data class TransactionLoadSuccess(val transactionVo: TransactionVo) : Initial

        data class AccountDataLoadSuccess(val accountData: AccountData) : Initial
    }

    sealed interface Categories : TransactionEditorAction {

        data object CategoriesLoadRequested : Categories

        data class CategoriesLoadSuccess(val categories: List<CategoryVo>) : Categories

        data class CategoriesLoadError(val error: AppError) : Categories
    }

    sealed interface Form : TransactionEditorAction {

        data class OnAmountChange(val amount: String) : Form

        data class OnDateChange(val date: String) : Form

        data class OnTimeChange(val time: String) : Form

        data class OnCategoryChange(val categoryId: Int, val categoryName: String) : Form

        data class OnCommentChanged(val comment: String) : Form
    }

    sealed interface Create : TransactionEditorAction {

        data object OnCreatePressed : Create

        data object OnCreateSuccess : Create

        data object OnCreateError : Create
    }

    sealed interface Update : TransactionEditorAction {

        data object OnUpdatePressed : Update

        data object OnUpdateSuccess : Update

        data object OnUpdateError : Update
    }

    sealed interface Delete : TransactionEditorAction {

        data object OnDeletePressed : Delete

        data object OnDeleteSuccess : Delete

        data object OnDeleteError : Delete
    }
}