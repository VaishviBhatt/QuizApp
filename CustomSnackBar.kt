package uk.ac.aber.dcs.CS31620.quizapp.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun CustomSnackBar(
    snackMessage: String,
    onUndo: (() -> Unit)?,
    showUndoAction: Boolean = true,
    snackbarHostState: SnackbarHostState
) {


    //  Show snackbar with action
    LaunchedEffect(snackMessage){
        snackbarHostState.showSnackbar(
            message = snackMessage,
            actionLabel = if (showUndoAction) "Undo" else null,
            duration = SnackbarDuration.Short

        )
    }
    // Display the Snackbar at the bottom of the screen
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier
            .padding(bottom = 80.dp) // Padding to ensure it's above FABs
            .clip(RoundedCornerShape(5.dp)),
        snackbar = { snackbarData ->
            Snackbar(
                action = {
                    if (showUndoAction) {
                        if (onUndo != null) {
                            TextButton(onClick = onUndo) {
                                Text(text = snackbarData.visuals.actionLabel ?: "Undo")
                            }
                        }
                    }
                },
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.onSurface
            ) {
                Text(text = snackbarData.visuals.message)
            }
        }
    )
}
