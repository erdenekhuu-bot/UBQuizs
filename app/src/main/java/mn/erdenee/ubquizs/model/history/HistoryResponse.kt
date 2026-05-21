package mn.erdenee.ubquizs.model.history

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @SerializedName("results")
    val results: MutableList<HistoryModel>
)
