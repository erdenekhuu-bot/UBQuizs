package mn.erdenee.ubquizs.api

import mn.erdenee.ubquizs.model.AnswerCheck
import mn.erdenee.ubquizs.model.AnswerCheckResponse
import mn.erdenee.ubquizs.model.AnswerResponse
import mn.erdenee.ubquizs.model.CategoryResponse
import mn.erdenee.ubquizs.model.HistoryResponse
import mn.erdenee.ubquizs.model.LevelResponse
import mn.erdenee.ubquizs.model.LoginRequest
import mn.erdenee.ubquizs.model.PointResponse
import mn.erdenee.ubquizs.model.ProfileResponse
import mn.erdenee.ubquizs.model.QuizResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
interface APIService {
    @GET("post/category")
    suspend fun getCategory(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<CategoryResponse>

    @GET("post/answer")
    suspend fun getAnswer(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<AnswerResponse>

    @GET("post/history")
    suspend fun getHistory(
        @Query("page") page:Int,
        @Query("pageSize") pageSize: Int
    ): Response<HistoryResponse>

    @GET("post/point")
    suspend fun getPoint(
        @Query("page") page:Int,
        @Query("pageSize") pageSize: Int
    ): Response<PointResponse>
    @GET("post/level/levelpass")
    suspend fun getLevel(
        @Query("profileid") profileid: Int): Response<LevelResponse>

    @GET("post/level/question")
    suspend fun getQuestionsByLevel(
        @Query("level") level:Int
    ): Response<QuizResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ProfileResponse>

    @POST("post/answer")
    suspend fun getCheckAnswer(
        @Body answerCheck: AnswerCheck
    ): Response<AnswerCheck>
}