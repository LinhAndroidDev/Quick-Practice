package com.example.quickpractice.ui.theme.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.quickpractice.data.dto.LoginRequest
import com.example.quickpractice.data.repository.AuthRepository
import com.example.quickpractice.ui.theme.navigation.Route
import com.example.quickpractice.ui.theme.screen.login.model.LoginModel
import com.example.quickpractice.util.SharePreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    class Success(val data: LoginModel?) : LoginState()
    class Failure(val message: String) : LoginState()
    class Loading() : LoginState()
    class Idle : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    @Inject
    lateinit var shared: SharePreferenceRepository

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle())
    val state = _state.asStateFlow()

    fun login(username: String, password: String) = viewModelScope.launch {
        _state.value = LoginState.Loading()
        try {
            val response = authRepository.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                val loginModel = response.body()
                if (loginModel?.role == 1) {
                    shared.saveToken(loginModel.auth?.accessToken ?: "")
                    shared.saveUserId(loginModel.userId ?: 0)
                    shared.saveUserName(loginModel.username ?: "")
                    _state.value = LoginState.Success(loginModel)
                } else {
                    _state.value = LoginState.Failure("Account does not have access, please try another account")
                }
            } else {
                _state.value = LoginState.Failure("Login failed: ${response.message()}")
            }
        } catch (e: Exception) {
            _state.value = LoginState.Failure(e.message ?: "An unexpected error occurred")
        }
    }

    fun goToRegister(navController: NavController) {
        navController.navigate(Route.Register.route)
    }

    fun goToHome(navController: NavController) {
        navController.navigate(Route.HOME.route) {
            popUpTo(Route.Login.route) {
                inclusive = true
            }
        }
    }
}