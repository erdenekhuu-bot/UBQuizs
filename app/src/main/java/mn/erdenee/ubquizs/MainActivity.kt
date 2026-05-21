package mn.erdenee.ubquizs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            val painter= painterResource(id=R.drawable.banner)
            val description="Background main banner"
            val title="UBQuiz game"
            UBQuizsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizApp(modifier = Modifier.padding(innerPadding))
                }
            }
//            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)){
//                bannerimage(painter,description,title)
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizApp(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
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

@Composable
fun bannerimage(
    painter: Painter,
    contentDescription: String?,
    title:String,
    modifier: Modifier = Modifier
){
    Card(
        modifier=modifier.fillMaxWidth(),
        shape=RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier= Modifier.height(200.dp).background(Brush.verticalGradient(listOf(Color.White, Color.LightGray), startY = 300f))
        ){
            Image(painter=painter, contentDescription=contentDescription, contentScale = ContentScale.Crop)
        }
    }
}