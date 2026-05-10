package mn.erdenee.ubquizs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mn.erdenee.ubquizs.ui.QuizUiState

@Composable
fun QuizScreen(
    uiState: QuizUiState,
    onAnswerSelected: (Int) -> Unit,
    onCheckAnswer: () -> Unit,
    onNext: () -> Unit
) {
    val currentQuestion = uiState.currentQuestion
    val currentLevel = uiState.currentLevel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Level ${currentLevel.levelNumber}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Question ${uiState.currentQuestionIndex + 1} of ${currentLevel.questions.size}",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = currentQuestion.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        currentQuestion.options.forEachIndexed { index, option ->
            val isSelected = uiState.selectedAnswerIndex == index
            val isCorrect = uiState.isAnswerChecked && index == currentQuestion.correctAnswerIndex
            val isWrongSelected = uiState.isAnswerChecked && isSelected && !uiState.isCorrect

            val backgroundColor = when {
                isCorrect -> Color(0xFF4CAF50) // Green
                isWrongSelected -> Color(0xFFF44336) // Red
                isSelected -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.surfaceVariant
            }

            val contentColor = when {
                isCorrect || isWrongSelected -> Color.White
                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            Button(
                onClick = { onAnswerSelected(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundColor,
                    contentColor = contentColor
                ),
                enabled = !uiState.isAnswerChecked
            ) {
                Text(text = option, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (uiState.isAnswerChecked) {
            val resultText = if (uiState.isCorrect) "Correct!" else "Incorrect!"
            val resultColor = if (uiState.isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
            
            Text(
                text = resultText,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = resultColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Next", fontSize = 18.sp)
            }
        } else {
            Button(
                onClick = onCheckAnswer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = uiState.selectedAnswerIndex != null
            ) {
                Text("Check Answer", fontSize = 18.sp)
            }
        }
    }
}
