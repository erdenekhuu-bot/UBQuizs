package mn.erdenee.ubquizs.model.category

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("results")
    val results: MutableList<CategoryModel>
)
