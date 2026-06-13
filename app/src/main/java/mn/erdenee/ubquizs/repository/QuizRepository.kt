package mn.erdenee.ubquizs.repository

import mn.erdenee.ubquizs.api.APIService
import mn.erdenee.ubquizs.model.AnswerCheck
import retrofit2.Response

class QuizRepository(private val apiService: APIService) {
    suspend fun getQuizData(profileId: Int) = apiService.getQuestionsByLevel(profileId)
    suspend fun getCheckoutAnswer(answerCheck: AnswerCheck): Response<AnswerCheck> {
        return apiService.getCheckAnswer(answerCheck)
    }

}