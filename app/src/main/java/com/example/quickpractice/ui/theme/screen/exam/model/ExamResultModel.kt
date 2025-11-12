package com.example.quickpractice.ui.theme.screen.exam.model

data class ExamResultModel(
    val id: Int,
    val exam: ExamModel,
    val userId: Int,
    val numberCorrectAnswers: Int,
    val numberWrongAnswers: Int,
    val totalQuestions: Int,
    val submittedAt: String
) {
    fun numberUnAnswered(): Int {
        return totalQuestions - (numberCorrectAnswers + numberWrongAnswers)
    }

    fun percentNumberCorrect(): Float {
        return (numberCorrectAnswers.toFloat() / totalQuestions.toFloat())
    }

    fun percentNumberWrong(): Float {
        return (numberWrongAnswers.toFloat() / totalQuestions.toFloat())
    }

    fun percentNumberUnAnswered(): Float {
        return (numberUnAnswered().toFloat() / totalQuestions.toFloat())
    }
}