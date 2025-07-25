package ru.point.transactions.ui.editor.viewmodel.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class TransactionEditorState(
    val form: FormState = FormState(),
    val categories: CategoriesState = CategoriesState(),
    val lastTimeSync: String = "",
)