package ru.point.ui.scaffold.bottombar

import androidx.compose.runtime.Immutable

@Immutable
sealed interface BottomBarState {

    data object Showed: BottomBarState

    data object Hidden: BottomBarState
}