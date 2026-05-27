package mn.erdenee.ubquizs.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mn.erdenee.ubquizs.repository.QuizRepository
import mn.erdenee.ubquizs.viewmodel.QuizViewModel

class QuizViewModelFactory(private val repository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}