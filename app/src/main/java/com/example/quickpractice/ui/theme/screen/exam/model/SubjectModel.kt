package com.example.quickpractice.ui.theme.screen.exam.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubjectModel(
    val id: Int,
    val nameSubject: String
) : Parcelable