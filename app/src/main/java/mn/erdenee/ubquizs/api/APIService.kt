package mn.erdenee.ubquizs.api

import mn.erdenee.ubquizs.model.answer.AnswerResponse
import mn.erdenee.ubquizs.model.category.CategoryResponse
import mn.erdenee.ubquizs.model.history.HistoryResponse
import mn.erdenee.ubquizs.model.level.LevelResponse
import mn.erdenee.ubquizs.model.point.PointResponse
import mn.erdenee.ubquizs.model.profile.LoginRequest
import mn.erdenee.ubquizs.model.profile.ProfileResponse
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

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<ProfileResponse>
}