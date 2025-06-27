package ru.point.ui.scaffold.fab

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
sealed interface FabState {
    data object Hidden : FabState

    data class Showed(val icon: ImageVector, val action: () -> Unit) : FabState
}