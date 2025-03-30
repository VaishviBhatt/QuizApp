package uk.ac.aber.dcs.CS31620.quizapp.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext


/**
 * This is implementation class of [QuizRepository]
 * This class privieds the methods to fetch, insert, delete and update the data.
 *
 * @property questionDao : handles the question related operations
 * @property answerDao : handles the answers related operations
 */
class QuizRepositoryImpl(
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao
) : QuizRepository {

    /**
     * Gets all the available questions from the database
     *
     * @return a [Flow] list of available questions
     */
    // Get all available questions
    override fun getAllQuestions(): Flow<List<Question>> {
        return questionDao.getAllQuestions().catch { emit(emptyList()) }
    }


    /**
     * Gets all the options for a particular question using the questionId
     *
     * @param questionId : ID of the question whose options you want
     * @return [Flow] List of all the answers for that particular qquestion
     */
    // get all possible answers for a specific question
    override fun getAnswersForCurrentQuestion(questionId: Int): Flow<List<Answer>> {
        return answerDao.getAnswersByQuestionId(questionId).catch { emit(emptyList()) }
    }


    /**
     * Inserts the options for a particular question
     * and are linked to the question using questionID
     *
     * @param answers : List of [Answer] to be inserted in the database
     */
    // Insert a bunch of answers into the data source
    override suspend fun insertAnswers(answers: List<Answer>) {
        answerDao.insertAnswers(answers)
    }


    /**
     * Adds new question and its options to the database.
     * and also marks the correct answer.
     *
     * @param qText : Text of [Question] to be inserted in the database
     * @param answers : List of [Answer] for the particular question
     * @param correctAnswerIndex : the correct [Answer]
     */
    // Add a new question and  set of answers and mark the correct answer
    override suspend fun addQuestion(qText: String, answers: List<String>, correctAnswerIndex: Int) {
        val newQuestion = Question(questionText = qText)


        val questionIdLong = questionDao.insertQuestion(newQuestion)
        val questionId = questionIdLong.toInt()


        val mappedAnswers = answers.mapIndexedNotNull { index, text ->
            if (text.isNotBlank()) {
                Answer(
                    questionID = questionId,
                    answerText = text,
                    isCorrect = index == correctAnswerIndex
                )
            } else {
                null
            }
        }

        withContext(Dispatchers.IO) {
            answerDao.insertAnswers(mappedAnswers)
        }
    }


    /**
     * This method updates the question.
     * Then deletes all the options associated with the questionID
     * and finally inserts the the updates list of options.
     *
     * @param question : the [Question] to be updated
     * @param answers : List of [Answer] to be update
     */
    // Update an existing question and its answers
    override suspend fun updateQuestion(question: Question, answers: List<Answer>) {
        questionDao.updateQuestion(question)
        answerDao.deleteAnswersByQuestionId(question.id)
        insertAnswers(answers)
    }



    // Remove a question and its answers from the database by its ID
    override suspend fun deleteQuestion(questionId: Int) {
        questionDao.deleteQuestion(questionId)
        answerDao.deleteAnswersByQuestionId(questionId)
    }
}