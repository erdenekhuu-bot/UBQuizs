package mn.erdenee.ubquizs.model.level


import com.google.gson.annotations.SerializedName

data class LevelResponse(
    @SerializedName("results")
    val results: MutableList<LevelModel>
)
