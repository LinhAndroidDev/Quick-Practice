package com.example.quickpractice.ui.theme.screen.exam.argument

import android.os.Parcelable
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamType
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamArgument(
    val exam: ExamModel,
    val type: ExamType,
) : Parcelable