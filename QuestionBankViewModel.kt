package uk.ac.aber.dcs.CS31620.quizapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.CS31620.quizapp.datasource.Answer
import uk.ac.aber.dcs.CS31620.quizapp.datasource.Question
import uk.ac.aber.dcs.CS31620.quizapp.datasource.QuizRepository

/**
 * This ViewModel is responsible for managing question Bank part of the app.
 * It interacts with the "Repository" and hence, "QuestionDAO" and "AnswerDAO" to perform CRUD operations
 * It provides methods to delete, edit, add wuestions and their respective options, aswell as select the correct option.
 *
 * @property QuizRepository The repository responsible for managing the DAOs
 *
 */

class QuestionBankViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {


    /**
     * Gets the list of all the questions from the database.
     * Queries the repository to get thw list of all the questions in the database.
     *
     */
    fun getAllQuestions() = quizRepository.getAllQuestions()



    /**
     * Updates the question and its option in the database.
     * Takes the question object and its answers and updates them in the repository.
     *
     *
     * @param question : question object to be updates
     * @param answers : List of ansers to be updates associated with the questionID
     */
    fun updateQuestion(question: Question, answers: List<Answer>) {
        viewModelScope.launch {
            quizRepository.updateQuestion(question, answers)
        }
    }


    /**
     * Deletes the question and the answers associated with it
     * It will remove the  question and its assisted answers in the database using the questionID.
     *
     * @param questionId : ID of the question and its answers to be deleted
     */

    fun deleteQuestion(questionId: Int) {
        viewModelScope.launch {
            quizRepository.deleteQuestion(questionId)
        }
    }

    /**
     * Gets the answers for a particular question by ID
     * Quries the repository to get the answers associated with the questionID
     *
     * @param questionId : ID of the question whose-options you want.
     */
    fun getAnswersByQuestionId(questionId: Int) = quizRepository.getAnswersForCurrentQuestion(questionId)



    /**
     * Adds a question and its associated options to the database.
     *
     *
     * @param qText : Text of the question object
     * @param answers : List of options for the question
     * @param correctAnswerIndex : the marked correct answer
     */
    fun addQuestion(qText: String, answers: List<String>, correctAnswerIndex: Int) {
        viewModelScope.launch {
            quizRepository.addQuestion(qText, answers, correctAnswerIndex)
        }
    }
}