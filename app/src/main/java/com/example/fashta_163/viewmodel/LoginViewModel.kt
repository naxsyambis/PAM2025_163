package com.example.fashta_163.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.repository.RepositoryAuth
import com.example.fashta_163.modeldata.ui.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repositoryAuth: RepositoryAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    // ===== Event dari UI =====

    fun onUsernameChange(value: String) {
        _uiState.update {
            it.copy(username = value, errorMessage = null)
        }
    }

    fun onPasswordChange(value: String) {
        _uiState.update {
            it.copy(password = value, errorMessage = null)
        }
    }

    fun login() {
        val state = _uiState.value

        // Validasi input kosong (sesuai SRS)
        if (state.username.isBlank() || state.password.isBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Username dan password wajib diisi")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val response = repositoryAuth.login(
                    username = state.username,
                    password = state.password
                )

                if (response.isSuccessful) {
                    _uiState.update {
                        it.copy(isLoginSuccess = true, isLoading = false)
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Username atau password salah"
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Terjadi kesalahan jaringan"
                    )
                }
            }
        }
    }

    fun resetLoginStatus() {
        _uiState.update {
            it.copy(isLoginSuccess = false)
        }
    }
}
