package ru.point.ui.di

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.ViewModelProvider

val LocalViewModelFactory =
    staticCompositionLocalOf<ViewModelProvider.Factory> {
        error("No ViewModel Factory provided")
    }