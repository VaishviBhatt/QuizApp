package uk.ac.aber.dcs.CS31620.quizapp.datasource
import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * ROOM database class
 * This database class serves as the main entry point for interacting with database through the DAOs.
 *
 * @property QuestionDao : DAO for question entity
 * @property AnswerDao : DAO for answer entity
 *
 */

@Database(entities = [Question::class, Answer::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val questionDao : QuestionDao
    abstract val answerDao : AnswerDao

}