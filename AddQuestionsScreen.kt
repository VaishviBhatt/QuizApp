package uk.ac.aber.dcs.CS31620.quizapp.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuestionBankViewModel

/**
 * AddQuestionScreen composable function.
 *
 * This screen allows users to add a new question along with its options and select the correct answer.
 * The screen includes field to enter new question, options and radio button to select the correct answer.
 * The question wont be saved in the database unless a question is entered, a option is entered and correct answer is checked.
 * It allows user to enter max 10 options for a question.
 *
 * @param navController is used to navigate to QuestionListScreen once the question is saved.
 * @param viewModel is used to interact with database and perform CRUD operations on questions and answers
 *
 */


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddQuestionScreen(navController: NavHostController, viewModel: QuestionBankViewModel) {
    // State for question and answers
    var questionText by remember { mutableStateOf("") }
    var answers = remember { mutableStateListOf("") }
    var selectedAnswerIndex by remember { mutableStateOf(-1) }


    // max number of options allowed
    val maxAnswers = 10

    // State for showing the Snackbar
    var snackMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    Scaffold(
        topBar = {
            // Navigate back to previous screen when clicked
            CustomTopAppBar(onBackClick = { navController.popBackStack() })

        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (questionText.isNotBlank() && selectedAnswerIndex != -1) {
                        // check that its not blank
                        val validAnswers = answers.filter { it.isNotBlank() }
                        viewModel.addQuestion(questionText, validAnswers, selectedAnswerIndex)

                        // Go back to the previous screen
                        navController.popBackStack()
                        // Display Snackbar

                    } else {
                        // display Snackbar
                        snackMessage = "Failed to save the question"
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(snackMessage)
                        }
                    }
                },
                modifier = Modifier.padding(16.dp),
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save Question")
            }
        }

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 70.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // question input
            Text("")
            TextField(
                value = questionText,
                onValueChange = { questionText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Enter the question") }
            )
            // answer input
            answers.forEachIndexed { index, answer ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    TextField(
                        value = answer,
                        onValueChange = { answers[index] = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Answer ${index + 1}") }
                    )


                    RadioButton(
                        selected = selectedAnswerIndex == index,
                        onClick = { selectedAnswerIndex = index }
                    )
                }
            }


            if (answers.size < maxAnswers) {
                Spacer(modifier = Modifier.height(16.dp))

                // Button to add a new answer
                Button(
                    onClick = {
                        if (answers.size < maxAnswers) {

                            answers.add("")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = answers.size < maxAnswers
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Option",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(text = "Add Answer")
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Maximum of $maxAnswers answers reached.",
                    color = Color.Gray,
                    style =   MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )

            }
           // if (showSnackbar) {
                //   CustomSnackBar(
                //  snackMessage = snackMessage,
                  //  onUndo = null,
                    //showUndoAction = false,
                    //snackbarHostState = snackbarHostState
                //)
            //}

        }


    }
}




