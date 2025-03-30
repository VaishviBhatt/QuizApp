package uk.ac.aber.dcs.CS31620.quizapp.datasource

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This is a dataclass that represents answers to a question.
 *  @Entity is use here o make it a Room entity.
 *  There is a foreign key relation to 'Question' entity, with the foreign key constraint cascading delete operations.
 *
 *  The 'Answer' entity hold the answer text, the flag that indicated which answer is correct and
 *  the 'questionID' to link it to a specific question.
 *
 *  @param id : Unique identifier for the answer generated automatically by room.
 *  @param questionID : This is the ID of the question, establishing relationship between 'Answer' and ,
 *  'Question'. This is foregin key to 'Question' entity.
 *
 *  @param answerText : Text of the answers
 *  @param isCorrect : This is the flag to mark the correct option.
 */

@Entity(
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["id"],
        childColumns = ["questionID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Answer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val questionID: Int,
    val answerText: String,
    val isCorrect: Boolean
)
