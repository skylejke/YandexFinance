package ru.point.settings.ui.appcolor.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.model.PrimaryColor

@Immutable
internal sealed interface AppColorAction {

    data class OnTogglePrimaryColor(val primaryColor: PrimaryColor) : AppColorAction

    data class OnGetPrimaryColor(val primaryColor: PrimaryColor) : AppColorAction
}