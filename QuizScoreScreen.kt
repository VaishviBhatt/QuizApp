package uk.ac.aber.dcs.CS31620.quizapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.CS31620.quizapp.R
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.*

/**
 * QuizScoreScreen Composable function.
 *
 * This screen displays the users score.
 *
 * This screen includes a card to show the user's score, with an image nd a message, and,
 * a button to start a new quiz.
 */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "Range")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScoreScreen(navController: NavHostController, score: Int) {
    Scaffold(
        topBar = {
            // the back button navigates to the quizStartScreen
            CustomTopAppBar(onBackClick = { navController.navigate("quizStartScreen") })
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
                // Card containing the score and image
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 70.dp)
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
                            text = "Your Score",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnSecondaryLight,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "$score",
                            style = MaterialTheme.typography.displayLarge,
                            color = OnSecondaryLight
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Image(
                            painter = painterResource(id = R.drawable.scoreimg),
                            contentDescription = "Score",
                            modifier = Modifier
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Great job! Keep going!",
                            style = TextStyle(fontSize = 14.sp),
                            color = GrayTextLight,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Button outside the Card
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        navController.navigate("quizStartScreen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryLight)
                ) {
                    Text(
                        text = "Start Again",
                        color = OnPrimaryLight,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            }
        }
    }
