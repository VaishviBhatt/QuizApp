package uk.ac.aber.dcs.CS31620.quizapp.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.CS31620.quizapp.datasource.Answer
import uk.ac.aber.dcs.CS31620.quizapp.datasource.Question
import uk.ac.aber.dcs.CS31620.quizapp.datasource.QuizRepository


/**
 * This viewModel is responsible for managing quiz part of the app.
 *
 * @property QuizRepository : The repository used to interact with the DAOs
 */

class QuizViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {
    private var _currentIndex = 0
    private val _questions = mutableListOf<Question>()
    private var _score = 0


    // current question in the quiz
    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> get() = _currentQuestion


    // indicates if the quiz is finished / run out of questions
    private val _isQuizFinished = MutableStateFlow(false)
    val isQuizFinished: StateFlow<Boolean> get() = _isQuizFinished

    // Total number of questions in the quiz
    private val _totalQuestions = MutableStateFlow(0)
    val totalQuestions: StateFlow<Int> get() = _totalQuestions


    // State to hold the answers for the current question
    var answers: MutableState<List<Answer>> = mutableStateOf(emptyList())

    // Current score
    private val _scoreFlow = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _scoreFlow


    /**
     * Starts the quiz by getting all the questions from the repository.
     * The questions are then shuffled and loaded onto the quiz.
     * The initial score = 0.
     * Updates the quiz progress and loads the questions.
     *
     */

    fun startQuiz() {
        viewModelScope.launch {
            quizRepository.getAllQuestions()
                .collect { questions ->

                    if (questions.isNotEmpty()) {
                        //reset
                       _questions.clear()
                        _score = 0
                        _currentIndex = 0
                        _isQuizFinished.value = false
                        _totalQuestions.value = questions.size
                        _questions.addAll(questions.shuffled())
                        loadNextQuestion()
                    } else {
                        _totalQuestions.value = 0
                    }
                }
        }
    }


    /**
     * Loads the next question in the quiz.
     * It checks if the quiz has finished, If all questions are answered/skipped the quiz is marked as finished.
     *
     */
    fun loadNextQuestion() {
        if (_currentIndex < _questions.size) {
            _currentQuestion.value = _questions[_currentIndex]
            _scoreFlow.value = _score
            _currentIndex++
        } else {
            _isQuizFinished.value = true
            _scoreFlow.value = _score
        }
    }


    /**
     *Gets the list of answers for the current question from the repository.
     * Returns the Flow of list of answers and returns an empty list if the current question is empty.
     *
     * @return [Flow] List of answers for the current question
     */
    fun getAnswersForCurrentQuestion(): Flow<List<Answer>> {
        return currentQuestion.value?.let {
            quizRepository.getAnswersForCurrentQuestion(it.id)
        } ?: flowOf(emptyList())
    }


    /**
     * Calculates the users score in the quiz.
     * If the selected answer is correct the score is changed. The score is not changed if the question is skipped.
     *
     * Once the score is counted, the next question is loaded.
     *
     * @param selectedAnswerId : ID of the selected answer by the user
     */
    fun calculateScore(selectedAnswerId: Int?) {
        viewModelScope.launch {
            val questionId = currentQuestion.value?.id ?: return@launch
            quizRepository.getAnswersForCurrentQuestion(questionId).collect { answers ->
                val correctAnswer = answers.firstOrNull { it.isCorrect }
                if (selectedAnswerId == null) {
                    _scoreFlow.value = _score
                } else if (correctAnswer?.id == selectedAnswerId) {
                    _score++
                    _scoreFlow.value = _score
                }
            }
            loadNextQuestion()
        }
    }

    /**
     * Moves to the next question without changing the score
     *
     */
    fun skipQuestion() {
        loadNextQuestion()
    }
}