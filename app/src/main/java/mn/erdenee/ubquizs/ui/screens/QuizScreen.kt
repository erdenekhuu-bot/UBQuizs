package mn.erdenee.ubquizs.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mn.erdenee.ubquizs.LocalStore
import mn.erdenee.ubquizs.model.AnswerModel
import mn.erdenee.ubquizs.ui.Screens
import mn.erdenee.ubquizs.viewmodel.QuizViewModel
@Composable
fun QuizScreen(navController: NavController, levelId: Int, viewModel: QuizViewModel){
    val context = LocalContext.current
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var selectedAnswer by remember { mutableStateOf<AnswerModel?>(null) }
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val score by viewModel.score.collectAsState()
    val localStore = remember { LocalStore(context.applicationContext) }
    val userId by localStore.userId.collectAsState(initial = null)
    val lives by viewModel.lives.collectAsState()
    val remainingSeconds by viewModel.remainingSeconds.collectAsState()
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60

    val formattedTime = String.format(
        "%02d:%02d",
        minutes,
        seconds
    )

    LaunchedEffect(levelId) {
        viewModel.loadQuestions(levelId)
    }

    val questions = categories.flatMap { category ->
        category.questions.map { question ->
            category to question
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }


    if (questions.isEmpty()) {
        return
    }

    if (lives <= 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Баяр хүргэе",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Таны оноо: $score",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        navController.navigate(Screens.Home.route) {
                            popUpTo(0) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text("Буцах")
                }
            }
        }
        return
    }

    //end show up score
    if (currentQuestionIndex >= questions.size) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Баяр хүргэя",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Оноо: $score",
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }) {
                    Text("Буцах")
                }
            }
        }
        return
    }

    val currentCategory = questions[currentQuestionIndex].first
    val currentQuestion = questions[currentQuestionIndex].second

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Асуулт ${currentQuestionIndex + 1}/${questions.size}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "⏱ $formattedTime",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (remainingSeconds <= 10) {
                    Color.Red
                } else {
                    Color(0xFF2879E0)
                }
            )

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = "❤️ $lives",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = currentCategory.category_name,
            color = Color(0xff4085de),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentQuestion.question_name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF1E293B),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                currentQuestion.answers.forEachIndexed { index, answer ->
                    val isSelected = selectedAnswer == answer
                    val optionLetter = ('A' + index).toString()

                    val borderColor =
                        if (isSelected) Color(0xFF0043CE) else Color.Transparent

                    val letterBgColor =
                        if (isSelected) Color(0xFF0043CE) else Color(0xFFE2E8F0)

                    val letterTextColor =
                        if (isSelected) Color.White else Color(0xFF1E293B)

                    val answerTextColor =
                        if (isSelected) Color(0xFF0043CE) else Color(0xFF1E293B)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            width = if (isSelected) 2.dp else 0.dp,
                            color = borderColor
                        ),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        onClick = {
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(50)
                                viewModel.checkoutAnswer(
                                    total = answer.total,
                                    answer_id = answer.id,
                                    profile_id = userId,
                                    question_id = answer.question_id,
                                    level_id = answer.level_id
                                )
                                selectedAnswer = null
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        letterBgColor,
                                        shape = RoundedCornerShape(8.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = optionLetter,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = letterTextColor
                                )
                            }

                            Text(
                                text = answer.text,
                                fontSize = 16.sp,
                                fontWeight = if (isSelected) {
                                    FontWeight.SemiBold
                                } else {
                                    FontWeight.Normal
                                },
                                color = answerTextColor,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 16.dp)
                            )

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Selected",
                                    tint = Color(0xFF0043CE),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
