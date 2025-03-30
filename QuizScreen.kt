package uk.ac.aber.dcs.CS31620.quizapp.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.Cards
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomConfirmationDialog
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuizViewModel

/**
 * QuizScreen composable function.
 *
 * This screen allows user to take the quiz.
 * This screen displays the questions and its options and allows user to interact with the screen.
 * User can select the option and then use FAB to navigate to the next question.
 * Once the quiz is finished the user is navigated to the QuizScoreScreen.
 *
 * @param viewModel : [QuizViewModel] that manages the quiz logic and interacts with the database.
 * @param navController : [NavHostController] used to navigate between screens.
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    navController: NavHostController
) {

    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val isQuizFinished by viewModel.isQuizFinished.collectAsState()
    val score by viewModel.score.collectAsState()
    val answers by viewModel.getAnswersForCurrentQuestion().collectAsState(initial = emptyList())

    //user selected answer ID
    var selectedAnswerId by remember { mutableStateOf<Int?>(null) }

    // State for showing the dialog
    var showDialog by remember { mutableStateOf(false) }


    // Start quiz when the screen loads
    LaunchedEffect(Unit) {
        viewModel.startQuiz()
    }

    // Update state when the question changes
    LaunchedEffect(currentQuestion) {
        if (currentQuestion != null) {
            selectedAnswerId = null
            viewModel.getAnswersForCurrentQuestion()
        }
    }

    // Navigate to score screen when quiz is finished
    LaunchedEffect(isQuizFinished) {
        if (isQuizFinished) {
            navController.navigate("QuizScoreScreen/$score")
        }
    }

   // LaunchedEffect(complete) {
     //   if (complete) {
       //     Log.d("QuizScreen", "Quiz Finished. Navigating to QuizScoreScreen with score: $score")
         //   navController.navigate("QuizScoreScreen/$score")
        //}
    //}

    Scaffold(
        topBar = {
            // the back button navigates to the main screen
            CustomTopAppBar(onBackClick = {
                showDialog = true})
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedAnswerId != null) {

                        viewModel.calculateScore(selectedAnswerId!!)
                        selectedAnswerId = null

                        viewModel.loadNextQuestion()
                    }else{
                        viewModel.skipQuestion()
                    }
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Next Question")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currentQuestion?.let { question ->
                // Display the question using Cards
                Cards(
                    title = question.questionText,
                    subtitle = null,
                    isEditable = false
                )

                // Display answer options using Cards with RadioButton
                answers.forEach { answer ->
                    Cards(
                        title = answer.answerText,
                        isEditable = false,
                        isRadioButtonVisible = true,
                        isSelected = selectedAnswerId == answer.id,
                        onRadioButtonClick = { selectedAnswerId = answer.id }
                    )
                }
            } ?: Text(
                text = "Please add questions to take the quiz",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
    // Use the ConfirmationDialog
    CustomConfirmationDialog(
        showDialog = showDialog,
        onDismiss = { showDialog = false },
        onConfirm = {
            // Inavigate back to the main screen
            navController.navigate("mainScreen") {
                popUpTo("quizStartScreen") { inclusive = true }
            }
        },
        title = "Are you sure you want to go back?",
        message = "You will have to start the quiz again."
    )
}