package com.example.quickpractice.data.repository

import com.example.quickpractice.data.ApiService
import com.example.quickpractice.ui.theme.screen.home.model.SubjectModel
import retrofit2.Response
import javax.inject.Inject

class SubjectResponseImpl @Inject constructor(private val apiService: ApiService) :
    SubjectRepository {
    override suspend fun getSubjects(): Response<List<SubjectModel>?> {
        val subjectResponse = apiService.getSubjects()
        val subjectModels = subjectResponse.body()?.data?.map { reponse ->
            SubjectModel(
                id = reponse.id,
                nameSubject = reponse.nameSubject
            )
        }
        return Response.success(subjectModels)
    }
}