package com.example.quickpractice.ui.theme.screen.exam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionModel(
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
    val subject: SubjectModel?,
    var answer: Choice?,
    var expand: Boolean = true,
) : Parcelable {
    fun copyWith(answer: Choice? = null, expand: Boolean? = null): QuestionModel {
        return QuestionModel(
            content = this.content,
            contentImage = this.contentImage,
            correctAnswer = this.correctAnswer,
            explanation = this.explanation,
            explanationImage = this.explanationImage,
            id = this.id,
            optionA = this.optionA,
            optionB = this.optionB,
            optionC = this.optionC,
            optionD = this.optionD,
            subject = this.subject,
            answer = answer ?: this.answer,
            expand = expand ?: this.expand
        )
    }

    fun isCorrect(): Boolean {
        return answer?.value == correctAnswer
    }

    fun isAnswered(): Boolean {
        return answer != null
    }
}