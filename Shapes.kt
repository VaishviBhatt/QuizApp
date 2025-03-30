package uk.ac.aber.dcs.CS31620.quizapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),   // Minimal rounding
    small = RoundedCornerShape(8.dp),       // Slightly rounded corners
    medium = RoundedCornerShape(16.dp),     // Pronounced rounded corners
    large = RoundedCornerShape(32.dp)       // Highly rounded corners
)
