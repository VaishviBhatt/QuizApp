package uk.ac.aber.dcs.CS31620.quizapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.ErrorLight
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.PrimaryLight
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.SurfaceContainerLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cards(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    isEditable: Boolean = false,
    onEditClick: (() -> Unit)? = null,
    onDeleteClick: (() -> Unit)? = null,
    onContentChange: ((String) -> Unit)? = null,
    isRadioButtonVisible: Boolean = false,
    isSelected: Boolean = false,
    onRadioButtonClick: (() -> Unit)? = null
) {

    var text by remember { mutableStateOf(title) }

    LaunchedEffect(title) {
        text = title
    }
    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        // Card containing the text, subtitle, and radio button
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLight),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)

        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isRadioButtonVisible) {
                            RadioButton(
                                selected = isSelected,
                                onClick = onRadioButtonClick,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            if (isEditable) {
                                TextField(
                                    value = text,
                                    onValueChange = { newValue ->
                                        text = newValue
                                        onContentChange?.invoke(newValue)
                                    },
                                    label = { Text("Title") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                            } else {
                                Text(
                                    text = text,
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            if (subtitle != null) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(subtitle, fontSize = 14.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }

        // Row containing the buttons (Edit, Delete)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            // Edit Button
            if (onEditClick != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(40.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryLight)
                        .clickable(onClick = onEditClick)
                        .padding(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Delete Button
            if (onDeleteClick != null) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(40.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ErrorLight)
                        .clickable(onClick = onDeleteClick)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        }
    }
}
