package ru.point.transactions.ui.editor.viewmodel.state

internal data class TransactionEditorState(
    val form: FormState = FormState(),
    val categories: CategoriesState = CategoriesState(),
    val lastTimeSync: String = "",
)