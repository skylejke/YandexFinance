package ru.point.transactions.ui.editor.viewmodel

internal sealed interface TransactionEditorEvent {

    data object ShowSuccessEditToastAndGoBack: TransactionEditorEvent

    data object ShowSuccessDeleteToastAndGoBack: TransactionEditorEvent

    data object ShowErrorDialog: TransactionEditorEvent

    data object ShowInValidFieldsToast: TransactionEditorEvent
}