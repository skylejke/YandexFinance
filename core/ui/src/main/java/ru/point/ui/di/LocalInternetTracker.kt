package ru.point.ui.di

import androidx.compose.runtime.staticCompositionLocalOf
import ru.point.utils.network.InternetTracker

val LocalInternetTracker = staticCompositionLocalOf<InternetTracker> {
    error("No InternetTracker provided")
}