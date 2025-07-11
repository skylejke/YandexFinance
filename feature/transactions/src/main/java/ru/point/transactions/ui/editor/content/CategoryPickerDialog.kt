package ru.point.transactions.ui.editor.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.point.transactions.ui.editor.viewmodel.state.CategoriesState
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.utils.model.toUserMessage

@Composable
internal fun CategoryPickerDialog(
    title: String,
    state: CategoriesState,
    onDismiss: () -> Unit,
    onConfirm: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = modifier
        ) {

            Column(
                modifier = Modifier
                    .heightIn(min = 400.dp)
                    .widthIn(min = 300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = title, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp))

                when {
                    state.isCategoriesLoading -> LoadingIndicator()

                    state.categoriesLoadError != null -> ErrorContent(
                        message = state.categoriesLoadError.toUserMessage(),
                        modifier = modifier.fillMaxSize()
                    )

                    else ->
                        CategoriesList(
                            categoriesList = state.categoriesList,
                            onItemClick = onConfirm,
                            modifier = Modifier.fillMaxWidth()
                        )
                }
            }
        }
    }
}