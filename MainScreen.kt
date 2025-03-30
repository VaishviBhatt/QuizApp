package uk.ac.aber.dcs.CS31620.quizapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uk.ac.aber.dcs.CS31620.quizapp.R
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.*

/**
 * This is the Main screen of the app where users can navigate to the 2 sections of the app.
 * the QuestionBank where thy will be able to add and edit questions,
 * and quiz where they will be able to take quiz.
 * @param navController is used to manage the navigation between screens.
 *
 * There are 2 cards on this screen, each card is clickable, and navigates the users to respective screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopAppBar(onBackClick = {  })
        },
    ) { padding->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {



                Spacer(modifier = Modifier.height(16.dp))

                // First Card: Question Bank
                Card(
                    modifier = Modifier
                        .width(330.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(330.dp)
                        // Navigate to QuestionListScreen
                        .clickable {
                            navController.navigate("questionListScreen")
                        },

                    shape = Shapes.large,
                    colors = CardDefaults.cardColors(containerColor = SecondaryLight),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Question Bank",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnSecondaryLight
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.question_bank_image),
                            contentDescription = "Question Bank Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Add or edit questions",
                            style = TextStyle(fontSize = 14.sp),
                            textAlign = TextAlign.Center,
                            color = GrayTextLight
                        )
                    }
                }

                // Second Card: Quiz Mode
                Card(
                    modifier = Modifier
                        .width(330.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(330.dp)
                        // navigate to quizStartScreen
                        .clickable { navController.navigate("quizStartScreen") },


                    shape = Shapes.large,
                    colors = CardDefaults.cardColors(containerColor = SecondaryLight),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Quiz Mode",
                            style = MaterialTheme.typography.headlineMedium,
                            color = OnSecondaryLight
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painter = painterResource(id = R.drawable.quiz_mode_image),
                            contentDescription = "Quiz Mode Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(200.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Are you ready to take the quiz?",
                            style = TextStyle(fontSize = 14.sp),
                            textAlign = TextAlign.Center,
                            color = GrayTextLight
                        )
                    }
                }
            }
        }
    }
}
