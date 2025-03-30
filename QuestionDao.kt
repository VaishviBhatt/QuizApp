package uk.ac.aber.dcs.CS31620.quizapp.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access object (DAO) for 'Question' entity
 *
 * This interface defines the operations that can be carried out on the "Question" table.
 * It provides methods for inserting, querying, updating, and deleting questions.
 */
@Dao
interface QuestionDao {

    /**
     * Inserts new question in 'Question' table.
     *The annotation @Insert will insert questions into database.
     *
     * @param question : question entity to be inserted
     * @return row ID of the inserted question.
     */
    @Insert
    suspend fun insertQuestion(question: Question): Long





    /**
     * Gets all the questions from 'Question' table.
     * The annotation @Query is used to execute SELECT query to get all the questions.
     * @return [Flow] that returns the list of all questions in the database.
     */
    @Query("SELECT * FROM Question")
    fun getAllQuestions(): Flow<List<Question>>




    /**
     * Deletes question from "question" table based on its ID
     * The annotation @Query is used to execute DELETE query using questionID.
     *
     * @param questionId : ID of the question to be deleted.
     */
    @Query("DELETE FROM Question WHERE id = :questionId")
    suspend fun deleteQuestion(questionId: Int)





    /**
     * Updates question in 'Question' table
     * The annotation @Update is used to execute UPDATE query for the given question.
     *
     *@param question : the updated question entity to be saved in the database
     */
    @Update
    suspend fun updateQuestion(question: Question)

}
