package mn.erdenee.ubquizs.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import mn.erdenee.ubquizs.data.QuizData
import mn.erdenee.ubquizs.model.Level
import mn.erdenee.ubquizs.model.Question

data class QuizUiState(
    val levels: List<Level> = QuizData.levels,
    val currentLevelIndex: Int = 0,
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val isAnswerChecked: Boolean = false,
    val isCorrect: Boolean = false,
    val isLevelComplete: Boolean = false,
    val isGameComplete: Boolean = false,
    val score: Int = 0
) {
    val currentLevel: Level
        get() = levels[currentLevelIndex]

    val currentQuestion: Question
        get() = currentLevel.questions[currentQuestionIndex]
}

class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun selectAnswer(index: Int) {
        if (_uiState.value.isAnswerChecked) return
        _uiState.update { it.copy(selectedAnswerIndex = index) }
    }

    fun checkAnswer() {
        val currentState = _uiState.value
        if (currentState.selectedAnswerIndex == null) return

        val isCorrect = currentState.selectedAnswerIndex == currentState.currentQuestion.correctAnswerIndex
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score

        _uiState.update {
            it.copy(
                isAnswerChecked = true,
                isCorrect = isCorrect,
                score = newScore
            )
        }
    }

    fun nextQuestionOrLevel() {
        val currentState = _uiState.value
        val nextQuestionIndex = currentState.currentQuestionIndex + 1

        if (nextQuestionIndex < currentState.currentLevel.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextQuestionIndex,
                    selectedAnswerIndex = null,
                    isAnswerChecked = false,
                    isCorrect = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLevelComplete = true
                )
            }
        }
    }

    fun nextLevel() {
        val currentState = _uiState.value
        val nextLevelIndex = currentState.currentLevelIndex + 1

        if (nextLevelIndex < currentState.levels.size) {
            _uiState.update {
                it.copy(
                    currentLevelIndex = nextLevelIndex,
                    currentQuestionIndex = 0,
                    selectedAnswerIndex = null,
                    isAnswerChecked = false,
                    isCorrect = false,
                    isLevelComplete = false
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    isLevelComplete = false,
                    isGameComplete = true
                )
            }
        }
    }
    fun restartGame() {
        _uiState.value = QuizUiState()
    }
}
