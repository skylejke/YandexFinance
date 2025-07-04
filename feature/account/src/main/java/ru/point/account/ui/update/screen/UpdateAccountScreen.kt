package ru.point.account.ui.update.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import ru.point.account.R
import ru.point.account.ui.update.content.UpdateAccountScreenContent
import ru.point.account.ui.update.viewmodel.UpdateAccountAction
import ru.point.account.ui.update.viewmodel.UpdateAccountEvent
import ru.point.account.ui.update.viewmodel.UpdateAccountState
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.utils.model.toUserMessage

@Composable
fun UpdateAccountScreen(
    state: UpdateAccountState,
    onAction: (UpdateAccountAction) -> Unit,
    events: Flow<UpdateAccountEvent>,
    onGoBackIfSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val isOnline by LocalInternetTracker.current.online.collectAsState()

    if (!isOnline) {
        NoInternetBanner(modifier = modifier)
    } else {
        when {
            state.isLoading -> {
                LoadingIndicator(modifier = modifier)
            }

            state.error != null -> {
                ErrorContent(
                    message = state.error.toUserMessage(),
                    modifier = modifier
                )
            }

            else -> {
                UpdateAccountScreenContent(
                    state = state,
                    onAction = onAction,
                    modifier = modifier
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                UpdateAccountEvent.ShowSuccessToastAndGoBack -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.successfully_update_account),
                        Toast.LENGTH_SHORT
                    ).show()
                    onGoBackIfSuccess()
                }

                UpdateAccountEvent.ShowErrorToast -> {
                    Toast.makeText(
                        context,
                        context.getString(R.string.something_went_wrong),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}