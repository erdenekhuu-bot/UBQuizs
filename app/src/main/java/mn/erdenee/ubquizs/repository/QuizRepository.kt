package mn.erdenee.ubquizs.repository

import mn.erdenee.ubquizs.api.APIService

class QuizRepository(private val apiService: APIService) {
    suspend fun getQuizData(profileId: Int) = apiService.getQuestionsByLevel(profileId)
}