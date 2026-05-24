package mn.erdenee.ubquizs.model.level

data class LevelModel(
    val profile_id:Number,
    val level_id: Number,
    val level_name: String,
    val level_required_total: Int,
    val user_earned: Number,
    val level_passed: Int
)
