package mn.erdenee.ubquizs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import mn.erdenee.ubquizs.ui.QuizViewModel
import mn.erdenee.ubquizs.ui.screens.GameCompleteScreen
import mn.erdenee.ubquizs.ui.screens.LevelCompleteScreen
import mn.erdenee.ubquizs.ui.screens.QuizScreen
import mn.erdenee.ubquizs.ui.theme.UBQuizsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UBQuizsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun QuizApp(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    androidx.compose.foundation.layout.Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isGameComplete -> {
                GameCompleteScreen(
                    uiState = uiState,
                    onRestart = { viewModel.restartGame() }
                )
            }
            uiState.isLevelComplete -> {
                LevelCompleteScreen(
                    uiState = uiState,
                    onNextLevel = { viewModel.nextLevel() }
                )
            }
            else -> {
                QuizScreen(
                    uiState = uiState,
                    onAnswerSelected = { viewModel.selectAnswer(it) },
                    onCheckAnswer = { viewModel.checkAnswer() },
                    onNext = { viewModel.nextQuestionOrLevel() }
                )
            }
        }
    }
}
