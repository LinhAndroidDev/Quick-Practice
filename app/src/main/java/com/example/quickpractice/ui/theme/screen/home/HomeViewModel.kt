package com.example.quickpractice.ui.theme.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.quickpractice.data.repository.SubjectRepository
import com.example.quickpractice.ui.theme.navigation.Route
import com.example.quickpractice.ui.theme.screen.home.model.SubjectModel
import com.example.quickpractice.util.SharePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val subjectRepository: SubjectRepository) :
    ViewModel() {
    private val _subjects: MutableStateFlow<List<SubjectModel>?> = MutableStateFlow(null)
    val subjects = _subjects.asStateFlow()

    @Inject
    lateinit var shared: SharePreferenceRepository

    init {
        fetchSubjects()
    }

    private fun fetchSubjects() = viewModelScope.launch {
        val response = subjectRepository.getSubjects()
        if (response.isSuccessful) {
            _subjects.value = response.body()
        } else {
            _subjects.value = null
        }
    }

    fun goToExamList(navController: NavController, subjectModel: SubjectModel) {
        navController.currentBackStackEntry
            ?.savedStateHandle
            ?.set("subject", subjectModel)
        navController.navigate(Route.EXAM_LIST.route)
    }

    fun goToExamHistory(navController: NavController) {
        navController.navigate(Route.EXAM_HISTORY.route)
    }

    fun goToLogin(navController: NavController) {
        clearData()
        navController.navigate(Route.Login.route) {
            popUpTo(Route.HOME.route) {
                inclusive = true
            }
        }
    }

    private fun clearData() {
        shared.clearToken()
        shared.clearUserId()
        shared.clearUserName()
    }
}