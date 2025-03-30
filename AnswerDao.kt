package uk.ac.aber.dcs.CS31620.quizapp.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Class Object (DAO) for 'Answer' entity
 *
 * This interface defines methods to interact with the 'Answer' table in the room database.
 * it allows inserting, querying, updating and deleting answers.
 *
 *
 */

@Dao
interface AnswerDao {

    /**
     * Inserts list of answer into the 'Answer' table
     * The annotation @Insert will insert answer into database.
     *
     * @param answers : list of [Answer] to be inserted in the database.
     */
    @Insert
    suspend fun insertAnswers(answers: List<Answer>)




    /**
     * Gets all answers assosiated with a specific questionID
     * The annotation @Query is used to execute SELECT query to get the answers
     *
     * @param questionId : ID of question whose options needed
     * @return a [Flow] list of all the answers for the given questionID
     *
     */
    @Query("SELECT * FROM Answer WHERE questionID = :questionId")
    fun getAnswersByQuestionId(questionId: Int): Flow<List<Answer>>



    /**
     * Deleted all the answers for a particular questionID
     * The annotation @Query is used to execute DELETE query.
     * @param questionId : ID of question whose answers are to be deleted
     */
    @Query("DELETE FROM Answer WHERE questionID = :questionId")
    suspend fun deleteAnswersByQuestionId(questionId: Int)




    /**
     * Updates an answer in the 'Answer' table
     * The annotation @Update is used to update the answer.
     *
     *@param answer : the [Answer] to be updated in the database
     */
    @Update
    suspend fun updateAnswer(answer: Answer)
}
