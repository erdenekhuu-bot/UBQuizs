package mn.erdenee.ubquizs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mn.erdenee.ubquizs.model.Category
import mn.erdenee.ubquizs.repository.QuizRepository

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _lives = MutableStateFlow(3)
    val lives = _lives.asStateFlow()
    fun loadQuestions(levelId: Int) {
        viewModelScope.launch {
            runCatching {
                _isLoading.value = true
                quizRepository.getQuizData(levelId)
            }.onSuccess {
                if (it.isSuccessful) {
                    val quizResult = it.body()?.result?.firstOrNull()
                    _categories.value = quizResult?.categories ?: emptyList()
                }
                _isLoading.value = false
            }.onFailure {
                e->
                Log.d("viewmodel","failed to load questions: ${e.message}")
                _isLoading.value = false
            }
        }
    }
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    fun checkAnswer(isCorrect: Int) {
        if (isCorrect==1) {
            _score.value += 10
        }
        _currentQuestionIndex.value += 1
    }

    fun decreaseLife() {
        if (_lives.value > 0) {
            _lives.value -= 1
        }
    }

    fun isGameOver(): Boolean {
        return _lives.value <= 0
    }
}