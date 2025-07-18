package ru.point.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.ui.colors.Mint
import ru.point.ui.di.LocalInternetTracker

@Composable
fun RequiredInternetContent(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {

    val isOnline by LocalInternetTracker.current.online.collectAsState()

    Column(modifier = modifier) {
        if (isOnline.not()) {
            NoInternetBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(Mint)
                    .padding(16.dp)
            )
        }

        content()
    }
}