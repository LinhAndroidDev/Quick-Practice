package com.example.quickpractice.data.response

import com.example.quickpractice.ui.theme.screen.exam.model.Choice
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.ui.theme.screen.exam.model.SubjectModel

data class ExamResponse(override val data: List<ExamData>?) : BaseResponse<List<ExamResponse.ExamData>>() {
    data class ExamData(
        val createdAt: String,
        val durationSeconds: Int,
        val id: Int,
        val questions: List<QuestionData>?,
        val subject: SubjectData?,
        val title: String
    ) {
        fun toExamModel(): ExamModel {
            return ExamModel(
                createdAt = this.createdAt,
                id = this.id,
                durationSeconds = this.durationSeconds,
                title = this.title,
                questions = this.questions?.map { q ->
                    QuestionModel(
                        content = q.content,
                        contentImage = q.contentImage,
                        correctAnswer = q.correctAnswer,
                        explanation = q.explanation,
                        explanationImage = q.explanationImage,
                        id = q.id,
                        optionA = q.optionA,
                        optionB = q.optionB,
                        optionC = q.optionC,
                        optionD = q.optionD,
                        subject = SubjectModel(
                            id = q.subject?.id ?: 0,
                            nameSubject = q.subject?.nameSubject ?: ""
                        ),
                        answer = if (q.answer == null || q.answer == -1) null else Choice.fromValue(q.answer)
                    )
                },
                subject = SubjectModel(
                    id = this.subject?.id ?: 0,
                    nameSubject = this.subject?.nameSubject ?: ""
                )
            )
        }
    }

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
        val subject: SubjectData?,
        val answer: Int?,
    )

    data class SubjectData(
        val id: Int,
        val nameSubject: String
    )
}