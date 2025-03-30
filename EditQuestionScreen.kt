package uk.ac.aber.dcs.CS31620.quizapp.Screens.QuestionBank

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import uk.ac.aber.dcs.CS31620.quizapp.datasource.Answer
import uk.ac.aber.dcs.CS31620.quizapp.datasource.Question
import uk.ac.aber.dcs.CS31620.quizapp.ui.components.CustomTopAppBar
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuestionBankViewModel

/**
 * EditQuestionScreen Composable function.
 *
 * This screen allows user to edit existing question and its options,
 * change the correct option and/or remove options.
 *
 * The screen loads question and its respective answers from the database in a editable fields,
 * that allows the user to save changes and update the database.
 *
 * @param navController is used to navigate back to questionListScreen to show the saved question.
 * @param viewModel is used to interact with database and perform CRUD operations on questions and answers.
 * @param questionId is the ID on the question that is being called. The options are connected to the questionId.
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuestionScreen(navController: NavController, viewModel: QuestionBankViewModel, questionId: Int) {
    // States to hold the question and answers
    var questionText by remember { mutableStateOf("") }
    var answers = remember { mutableStateListOf<String>() }
    var selectedAnswerIndex by remember { mutableStateOf(-1) }

    // Load question and answers when the screen is launched
    LaunchedEffect(questionId) {
        // get question details from viewModel
        val question = runBlocking { viewModel.getAllQuestions().first().find { it.id == questionId } }
        question?.let {
            questionText = it.questionText
        }
        // get answer details from viewModel
        val dbAnswers = runBlocking { viewModel.getAnswersByQuestionId(questionId).first() }
        answers.clear()
        dbAnswers.forEach { answer ->
            answers.add(answer.answerText)
            if (answer.isCorrect) {
                selectedAnswerIndex = dbAnswers.indexOf(answer)
            }
        }
    }
    // max number of options user can input
    val maxAnswers = 10

    Scaffold(
        topBar = {
            // navigates back to previous screen when clicked
            CustomTopAppBar(onBackClick = { navController.popBackStack() })
        },
        floatingActionButton = {
            FloatingActionButton(
                // save question when FAB is clicked
                onClick = {
                    if (questionText.isNotBlank() && selectedAnswerIndex != -1) {
                        // Prepare updated question and answers
                        val updatedQuestion = Question(id = questionId, questionText = questionText)
                        val updatedAnswers = answers.mapIndexed { index, text ->
                            Answer(
                                questionID = questionId,
                                answerText = text,
                                isCorrect = index == selectedAnswerIndex
                            )
                        }
                        // Update the question and answers in the database
                        viewModel.updateQuestion(updatedQuestion, updatedAnswers)

                        // Navigate back
                        navController.popBackStack()
                    }
                },
                containerColor = Color.Black,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Save Changes")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Question Text Field
            TextField(
                value = questionText,
                onValueChange = { questionText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Edit Question") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Answer Fields
            for (index in answers.indices) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    TextField(
                        value = answers[index],
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
            // Allow adding more answers if there are fewer than 10 answers
            if (answers.size < maxAnswers) {
                Spacer(modifier = Modifier.height(16.dp))

                // Button to add a new answer
                Button(
                    onClick = {
                        if (answers.size < maxAnswers) {
                            // Add an empty answer field
                            answers.add("")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    // Disable the button when reaching the max limit
                    enabled = answers.size < maxAnswers
                ) {
                    Text(text = "Add Answer")
                }
            } else {
                // If the maximum number of answers has been reached, disable adding
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Maximum of $maxAnswers answers reached.", color = Color.Gray)
            }
        }
    }
}





