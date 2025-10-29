package com.example.quickpractice.data.repository

import com.example.quickpractice.ui.theme.screen.home.model.SubjectModel
import retrofit2.Response

interface SubjectRepository {
    suspend fun getSubjects() : Response<List<SubjectModel>?>
}