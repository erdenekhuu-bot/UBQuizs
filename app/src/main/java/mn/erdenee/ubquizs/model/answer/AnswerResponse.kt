package mn.erdenee.ubquizs.model.answer

import com.google.gson.annotations.SerializedName


data class AnswerResponse(
    @SerializedName("results")
    val result: MutableList<AnswerModel>
)