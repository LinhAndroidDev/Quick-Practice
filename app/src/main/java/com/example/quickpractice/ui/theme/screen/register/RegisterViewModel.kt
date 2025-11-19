package com.example.quickpractice.ui.theme.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickpractice.data.dto.RegisterRequest
import com.example.quickpractice.data.repository.AuthRepository
import com.example.quickpractice.data.response.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    class Success(val data: RegisterResponse.RegisterData) : LoginState()
    class Failure(val message: String) : LoginState()
    class Loading() : LoginState()
    class Idle : LoginState()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle())
    val state = _state.asStateFlow()

    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) = viewModelScope.launch {
        _state.value = LoginState.Loading()
        try {
            val registerRequest = RegisterRequest(
                name = username,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                role = 1
            )
            val response = authRepository.register(registerRequest)
            if (response.isSuccessful) {
                val registerResponse = response.body()
                if (registerResponse?.data?.userId != 0) {
                    _state.value = LoginState.Success(registerResponse?.data!!)
                } else {
                    _state.value =
                        LoginState.Failure(registerResponse.message ?: "Registration failed")
                }
            } else {
                _state.value = LoginState.Failure("Registration failed: ${response.message()}")
            }
        } catch (e: Exception) {
            _state.value = LoginState.Failure(e.message ?: "An unexpected error occurred")
        }
    }
}