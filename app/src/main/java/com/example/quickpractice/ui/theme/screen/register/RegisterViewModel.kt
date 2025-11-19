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

sealed class RegisterState {
    class Success(val data: RegisterResponse.RegisterData) : RegisterState()
    class Failure(val message: String) : RegisterState()
    class Loading() : RegisterState()
    class Idle : RegisterState()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Idle())
    val state = _state.asStateFlow()

    fun register(
        username: String,
        email: String,
        password: String,
        confirmPassword: String,
    ) = viewModelScope.launch {
        _state.value = RegisterState.Loading()
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
                    _state.value = RegisterState.Success(registerResponse?.data!!)
                } else {
                    _state.value =
                        RegisterState.Failure(registerResponse.message ?: "Registration failed")
                }
            } else {
                _state.value = RegisterState.Failure("Registration failed: ${response.body()?.message}")
            }
        } catch (e: Exception) {
            _state.value = RegisterState.Failure(e.message ?: "An unexpected error occurred")
        }
    }
}