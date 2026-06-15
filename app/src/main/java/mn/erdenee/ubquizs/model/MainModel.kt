package mn.erdenee.ubquizs.model

import com.google.gson.annotations.SerializedName
data class AnswerModel(
    val id: Int,
    val answer_text: String,
    val is_correct: Boolean,
    val question_id: Int
)

data class AnswerResponse(
    @SerializedName("results")
    val result: MutableList<AnswerModel>
)

data class CategoryModel(
    val id: Int,
    val name: String,
    val created: String
)

data class CategoryResponse(
    @SerializedName("results")
    val results: MutableList<CategoryModel>
)



data class HistoryResponse(
    @SerializedName("data")
    val data: HistoryData
)

data class HistoryData(
    val total_point: Int,
    val all_points:Int,
    val history: List<HistoryModel>
)
data class HistoryModel(
    val question_point: Int,
    val category_name: String,
    val attempts_count: Int,
    val level_name: String,
    val level_total: Int
)
data class LevelModel(
    val level_id: Int,
    val level_name: String,
    val level_required_total: Int,
    val earned: Int,
    val passed: Int
)

data class LevelResponse(
    @SerializedName("results")
    val results: MutableList<LevelModel>
)


data class PointModel (
    val id: Int,
    val total: Int,
    val profile_id: Int
)

data class PointResponse(
    @SerializedName("results")
    val result: MutableList<PointModel>
)

data class LoginRequest(
    val username:String,
    val password:String
)

data class ProfileResponse(
    val token:String,
    val id:Int,
    val username: String
)

data class Category(
    val category_id: Int,
    val category_name: String,
    val questions: List<Question>
)
data class LevelData(
    val level_id: Int,
    val level_name: String,
    val categories: List<Category>
)

data class QuizResponse(
    val result: List<LevelData>
)

data class Question(
    val question_id: Int,
    val question_name: String,
    val answers: List<Answer>
)

data class Answer(
    val id: Int,
    val text: String,
    val total:Int,
    val question_id:Int,
    val is_correct: Int,
    val level_id:Int,
)

data class AnswerCheck(
    val profile_id: Int?,
    val question_id: Int,
    val answer_id: Int,
    val level_id: Int,
    val total: Int
)
