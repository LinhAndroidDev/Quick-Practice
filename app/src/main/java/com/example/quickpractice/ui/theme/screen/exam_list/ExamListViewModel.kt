package com.example.quickpractice.ui.theme.screen.exam_list

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.quickpractice.data.repository.ExamRepository
import com.example.quickpractice.ui.theme.navigation.Route
import com.example.quickpractice.ui.theme.screen.exam.argument.ExamArgument
import com.example.quickpractice.ui.theme.screen.exam.model.ExamModel
import com.example.quickpractice.ui.theme.screen.exam.model.ExamType
import com.example.quickpractice.ui.theme.screen.home.model.SubjectModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(private val examRepository: ExamRepository) :
    ViewModel() {
    private val _exams: MutableStateFlow<List<ExamModel>?> = MutableStateFlow(null)
    val exams = _exams.asStateFlow()
    private val _title: MutableStateFlow<String> = MutableStateFlow("")
    val title = _title.asStateFlow()

    private suspend fun fetchExams(subjectId: Int) {
        val response = examRepository.getExams(subjectId)
        if (response.isSuccessful) {
            _exams.value = response.body()
        } else {
            _exams.value = null
        }
    }

    suspend fun getArgument(navController: NavController) {
        val subject = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<SubjectModel>("subject")
        _title.value = subject?.nameSubject ?: ""

        fetchExams(subject?.id ?: -1)
    }

    fun goToExam(navController: NavController, exam: ExamModel) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.set("exam", ExamArgument(exam, ExamType.PRACTICE))
        navController.navigate(Route.EXAM.route)
    }
}