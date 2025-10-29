package com.example.quickpractice.data.repository

import com.example.quickpractice.data.ApiService
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.QuestionModel
import com.example.quickpractice.ui.theme.screen.exam.model.SubjectModel
import retrofit2.Response
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(private val apiService: ApiService) : ExamRepository {
    override suspend fun getExams(subjectId: Int): Response<List<ExamModel>?> {
        val examResponse = apiService.getExams(subjectId)
        val exam = examResponse.body()?.data?.map { e ->
            ExamModel(
                createdAt = e.createdAt,
                id = e.id,
                durationSeconds = e.durationSeconds,
                title = e.title,
                questions = e.questions?.map { q ->
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
                        )
                    )
                },
                subject = SubjectModel(
                    id = e.subject?.id ?: 0,
                    nameSubject = e.subject?.nameSubject ?: ""
                )
            )
        }
        return Response.success(exam)
    }
}