package uk.ac.aber.dcs.CS31620.quizapp.Screens.QuestionBank

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.Cards
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.BackgroundLight
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuestionBankViewModel

/**
 * QuestionListScreen composable function.
 *
 * This screen displays the list of questions in the question bank.
 *
 *It provides a top bar, ist of questions from [QuestionBankViewModel], and
 * a Floating Action Button FAB to navigate to "AddQuestion" Screen
 *
 * @param navController : a [NavHostController] for navigation between screens.
 * @param viewModel : The [QuestionBankViewModel] for managing questions.
 */

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuestionListScreen(navController: NavHostController, viewModel: QuestionBankViewModel) {

    // Collect the flow of questions from the ViewModel
    val questions by viewModel.getAllQuestions().collectAsState(initial = emptyList())

    // State for showing the Snackbar
    var snackMessage by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }



    // Main Scaffold with FAB for adding questions
    Scaffold(
        topBar = {
            // navigates back to previous screen when clicked
            CustomTopAppBar(onBackClick = { navController.popBackStack() })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addQuestionScreen")
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Question")
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundLight)
        ) {
            if (questions.isEmpty()) {
                // Display a message when no questions are available
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No questions available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Tap the '+' button to add a new question.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            } else {
                // LazyColumn for the list of questions
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 70.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(questions) { index, question ->
                        Cards(
                            title = question.questionText,
                            onEditClick = {
                                          navController.navigate("editQuestionScreen/${question.id}")
                            },
                            onDeleteClick = {
                                viewModel.deleteQuestion(question.id)
                                //show snackbar
                                snackMessage = "Question Deleted"
                                showSnackbar = true
                            }

                        )
                    }

                }
            }
        }

    }



}
