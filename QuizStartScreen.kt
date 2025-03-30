package uk.ac.aber.dcs.CS31620.quizapp.Screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.CS31620.quizapp.R
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.BackgroundLight
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.OnSecondaryLight
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.SurfaceContainerLight

/**
 * QuizStartScreen composable function
 *
 *This screen allows user to start quiz whenever they are ready.
 * Allows user to start quiz by clicking the "Start Quiz" button, and navigate to main screen using back.
 *
 * @param navController : [NavHostController] used to navigate between screens
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuizStartScreen(navController: NavHostController) {

    Scaffold(
        topBar = {
            // the back button navigates to the main screen
            CustomTopAppBar(onBackClick = { navController.navigate("mainScreen") })
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(350.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceContainerLight),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Welcome to your quiz portal!",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnSecondaryLight,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )


                        Image(
                            painter = painterResource(id = R.drawable.quiz_mode_image),
                            contentDescription = "Welcome Image",
                            modifier = Modifier
                                .size(150.dp)
                                .padding(bottom = 16.dp),
                            contentScale = ContentScale.Crop
                        )

                        Button(
                            onClick = {
                                // Navigate  to quiz screen
                                navController.navigate("quizScreen")
                                {
                                    popUpTo("quizStartScreen") { inclusive = true }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(text = "Start Quiz")
                        }
                    }
                }
            }
        }
    }
}