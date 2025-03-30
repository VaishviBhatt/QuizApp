package uk.ac.aber.dcs.CS31620.quizapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.ac.aber.dcs.CS31620.quizapp.Screens.AddQuestionScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.MainScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.QuestionBank.EditQuestionScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.QuestionBank.QuestionListScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.QuizScoreScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.QuizScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.QuizStartScreen
import uk.ac.aber.dcs.CS31620.quizapp.Screens.SplashScreen
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuestionBankViewModel
import uk.ac.aber.dcs.CS31620.quizapp.viewModels.QuizViewModel

/**
 * AppNavGraph is responsible for Navigation of the entire app.
 * It defines all the screens and the navigationpaths between them using Compose and NavController.
 *
 * @param navController : Controls navigation between screens
 * @param questionBankViewModel : Manages  the question bank part of the app
 * @param quizViewModel : Manges the quiz part of the app
 */

@Composable
fun AppNavGraph(navController: NavHostController,
                questionBankViewModel: QuestionBankViewModel,
                quizViewModel: QuizViewModel,
                ) {
    NavHost(navController = navController, startDestination = "splashScreen") {

        // Splash Screen : first screen
        composable("splashScreen") {
            SplashScreen(navController = navController)
        }

        // Main Screen
        composable("mainScreen") {
            MainScreen(navController = navController)
        }

        // QuizStartScreen
        composable("quizStartScreen") {
            QuizStartScreen(navController = navController)
        }

        // QuestionListScreen
        composable("questionListScreen") {
            QuestionListScreen(navController = navController, viewModel = questionBankViewModel)
        }

        // AddQuestionScreen
        composable("addQuestionScreen") {
            AddQuestionScreen(navController = navController, viewModel = questionBankViewModel)
        }

        // EditQuestionScreen with questionId as an argument to identify which question si to be edited
        composable(
            route = "editQuestionScreen/{questionId}",
            arguments = listOf(navArgument("questionId") { type = NavType.IntType })
        ) { backStackEntry ->
            val questionId = backStackEntry.arguments?.getInt("questionId") ?: -1
            EditQuestionScreen(navController = navController, viewModel = questionBankViewModel, questionId = questionId)
        }

        // QuizScreen
        composable("quizScreen") {
            QuizScreen(
                //appDatabase = appDatabase,
                viewModel = quizViewModel,
                //onQuizCompleted = { navController.navigate("quizScoreScreen") },
                navController = navController
            )
        }


        // QuizScoreScreen : expects score to be passed to display user score
        composable("quizScoreScreen/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            QuizScoreScreen(navController = navController, score = score)
        }

    }


}

