package com.example.quickpractice.ui.theme.screen.exam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamModel(
    val createdAt: String,
    val durationSeconds: Int,
    val id: Int,
    val questions: List<QuestionModel>?,
    val subject: SubjectModel?,
    val title: String
) : Parcelable