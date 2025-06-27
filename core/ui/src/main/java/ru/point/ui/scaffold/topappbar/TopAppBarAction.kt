package ru.point.ui.scaffold.topappbar

import androidx.compose.ui.graphics.vector.ImageVector

data class TopAppBarAction(
    val icon: ImageVector,
    val action: () -> Unit,
    val tag: String = ""
)