package uk.ac.aber.dcs.CS31620.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import uk.ac.aber.dcs.CS31620.quizapp.datasource.AppDatabase
import uk.ac.aber.dcs.CS31620.quizapp.datasource.QuizRepository
import uk.ac.aber.dcs.CS31620.quizapp.datasource.QuizRepositoryImpl
import uk.ac.aber.dcs.CS31620.quizapp.ui.theme.MyAppTheme
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuestionBankViewModel
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuizViewModel

/**
 * MainActivity is the entry point for the app.
 * It is responsible for setting up the main UI and initializing view-models anf apps Navigation Graph.
 * Sets up apps theme, database and view-models and contains the navigation setup.
 *
 */

class MainActivity : ComponentActivity() {

    /**
     * Initializes apps database instance. Uses ROOM to create local database.
     */
    private val db by lazy {
        Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java,
        "question_bank_db"
    ).build()  }

    private val quizRepository: QuizRepository by lazy {
        QuizRepositoryImpl(db.questionDao, db.answerDao)
    }
    /**
     * The QuizViewModel is responsible for managing the quiz.
     * This ViewModel is created using a custom factory that provides dependencies,
     * like the DAO for questions and answers.
     */
    // Initialize the QuizViewModel with its dependencies
    private val quizViewModel by viewModels<QuizViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return QuizViewModel(quizRepository) as T
                }
            }
        }
    )

    /**
     * The QuestionBankViewModel is responsible for managing the question bank (the list of questions).
     * This ViewModel is created using a custom factory that provides dependencies,
     * like the DAO for questions and answers.
     */
    // Initialize the QuestionBankViewModel with its dependencies
    private val questionBankViewModel by viewModels<QuestionBankViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return QuestionBankViewModel(quizRepository) as T
                }
            }
        }
    )

    /**
     * OnCreate method is called when the activity is created.
     * It sets up content view, initializes apps theme, nd sets up navigation.
     *
     * @param savedInstanceState : Saved state from previous instance if any.
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // MyAppTheme
            MyAppTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController,
                    questionBankViewModel= questionBankViewModel,
                    quizViewModel = quizViewModel
                     )
            }
        }
    }


}