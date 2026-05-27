package mn.erdenee.ubquizs.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import mn.erdenee.ubquizs.viewmodel.QuizViewModel

@Composable
fun QuizScreen(navController: NavController, levelId: Int, viewModel: QuizViewModel){
    val categories by viewModel.categories.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(levelId) {
        viewModel.loadQuestions(levelId)
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(categories) { category ->
                Text(
                    text = category.category_name,
                )

                category.questions.forEach { question ->
                    Card(modifier = Modifier.padding(vertical = 8.dp)) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = question.question_name)

                            question.answers.forEach { answer ->
                                OutlinedButton(
                                    onClick = { Log.d("check", answer.is_correct.toString()) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(text = answer.text)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
