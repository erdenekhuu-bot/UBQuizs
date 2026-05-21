package mn.erdenee.ubquizs.model.answer

data class AnswerModel(
    val id: Number,
    val answer_text: String,
    val is_correct: Boolean,
    val question_id: Number
)
