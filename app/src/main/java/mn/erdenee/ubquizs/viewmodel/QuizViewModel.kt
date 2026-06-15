package mn.erdenee.ubquizs.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mn.erdenee.ubquizs.model.AnswerCheck
import mn.erdenee.ubquizs.model.Category
import mn.erdenee.ubquizs.repository.QuizRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _lives = MutableStateFlow(3)
    val lives: StateFlow<Int> = _lives.asStateFlow()

    private val _remainingSeconds = MutableStateFlow(60)
    val remainingSeconds: StateFlow<Int> = _remainingSeconds.asStateFlow()

    private var timerJob: Job? = null
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    fun checkoutAnswer(
        total: Int,
        answer_id: Int,
        profile_id: Int?,
        question_id: Int,
        level_id: Int
    ) {
        viewModelScope.launch {
            runCatching {
                val request = AnswerCheck(
                    profile_id = profile_id,
                    question_id = question_id,
                    answer_id = answer_id,
                    level_id = level_id,
                    total = total
                )

                quizRepository.getCheckoutAnswer(request)

            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val checkout = response.body()

                    if (checkout?.is_correct == 1) {
                        _score.value += total
                    } else {
                        decreaseLife()
                    }

                    if (!isGameOver()) {
                        _currentQuestionIndex.value += 1
                    }
                }
            }.onFailure { e ->
                Log.d(
                    "viewmodel",
                    "failed to check answer: ${e.message}"
                )
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()

        timerJob = viewModelScope.launch {
            while (_remainingSeconds.value > 0) {
                delay(1000)
                _remainingSeconds.value -= 1
            }
            _lives.value = 0
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
    }

    fun decreaseLife() {
        if (_lives.value > 0) {
            _lives.value -= 1
        }
    }

    fun isGameOver(): Boolean {
        return _lives.value <= 0
    }

    fun loadQuestions(levelId: Int) {
        viewModelScope.launch {
            timerJob?.cancel()

            _score.value = 0
            _lives.value = 3
            _currentQuestionIndex.value = 0
            _remainingSeconds.value = 60
            _categories.value = emptyList()

            runCatching {
                _isLoading.value = true
                quizRepository.getQuizData(levelId)
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    val quizResult = response.body()?.result?.firstOrNull()
                    _categories.value = quizResult?.categories ?: emptyList()

                    if (_categories.value.isNotEmpty()) {
                        startTimer()
                    }
                }

                _isLoading.value = false
            }.onFailure { e ->
                Log.d(
                    "viewmodel",
                    "failed to load questions: ${e.message}"
                )
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}

