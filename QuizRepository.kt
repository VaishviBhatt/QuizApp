package uk.ac.aber.dcs.CS31620.quizapp.datasource

import kotlinx.coroutines.flow.Flow

/**
 * Interface forinteracting with quiz datasource.
 * This allows performing actions related to question and answers
 *
 */
interface QuizRepository {
    fun getAllQuestions(): Flow<List<Question>>
    fun getAnswersForCurrentQuestion(questionId: Int): Flow<List<Answer>>
    suspend fun insertAnswers(answers: List<Answer>)
    suspend fun updateQuestion(question: Question, answers: List<Answer>)
    suspend fun deleteQuestion(questionId: Int)
    suspend fun addQuestion(qText: String, answers: List<String>, correctAnswerIndex: Int)
}