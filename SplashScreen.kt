package uk.ac.aber.dcs.CS31620.quizapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.CS31620.quizapp.R
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.BackgroundLight

/**
 * SplashScreen Composable function.
 *
 * A flash screen displays app logo in the center.
 * and transitions to main screen after a short delay
 *
 * @param navController : [NavHostController] used to navigate between screens
 */

@Composable
fun SplashScreen(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(BackgroundLight),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.logosplashscreen),
            contentDescription = "Logo",
            modifier = Modifier
                .size(2000.dp)
        )
        // Delay of 800ms and then navigate to MainScreen
        LaunchedEffect(Unit) {
            kotlinx.coroutines.delay(1000)
            navController.navigate("mainScreen")
        }
    }
}