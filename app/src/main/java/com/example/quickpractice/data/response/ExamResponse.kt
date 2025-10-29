package com.example.quickpractice.data.response

data class ExamResponse(override val data: List<ExamData>?) : BaseResponse<List<ExamResponse.ExamData>>() {
    data class ExamData(
        val createdAt: String,
        val durationSeconds: Int,
        val id: Int,
        val questions: List<QuestionData>?,
        val subject: SubjectData?,
        val title: String
    )

    data class QuestionData(
        val content: String?,
        val contentImage: String?,
        val correctAnswer: Int,
        val explanation: String?,
        val explanationImage: String?,
        val id: Int,
        val optionA: String,
        val optionB: String,
        val optionC: String,
        val optionD: String,
        val subject: SubjectData?
    )

    data class SubjectData(
        val id: Int,
        val nameSubject: String
    )
}