package mn.erdenee.ubquizs.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mn.erdenee.ubquizs.ui.QuizUiState

@Composable
fun LevelCompleteScreen(
    uiState: QuizUiState,
    onNextLevel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Level ${uiState.currentLevel.levelNumber} Completed!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Score: ${uiState.score}",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onNextLevel,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Next Level", fontSize = 18.sp)
        }
    }
}
