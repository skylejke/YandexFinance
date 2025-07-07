package ru.point.account.ui.update.viewmodel

internal sealed interface UpdateAccountEvent {

    data object ShowSuccessToastAndGoBack: UpdateAccountEvent

    data object ShowErrorToast: UpdateAccountEvent
}