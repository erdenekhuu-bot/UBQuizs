package mn.erdenee.ubquizs.model.point

import com.google.gson.annotations.SerializedName

data class PointResponse(
    @SerializedName("results")
    val result: MutableList<PointModel>
)
