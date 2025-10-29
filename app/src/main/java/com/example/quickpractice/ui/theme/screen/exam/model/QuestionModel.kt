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
    val subject: SubjectModel?
) : Parcelable