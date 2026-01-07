package com.example.fashta_163.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fashta_163.modeldata.ui.RegisterUiState
import com.example.fashta_163.repository.RepositoryAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repositoryAuth: RepositoryAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onUsernameChange(value: String) {
        _uiState.update { it.copy(username = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value) }
    }

    fun register() {
        val state = _uiState.value

        // 🔐 VALIDASI FORM (WAJIB)
        if (state.username.isBlank() ||
            state.password.isBlank() ||
            state.confirmPassword.isBlank()
        ) {
            _uiState.update {
                it.copy(errorMessage = "Semua field wajib diisi")
            }
            return
        }

        if (state.password != state.confirmPassword) {
            _uiState.update {
                it.copy(errorMessage = "Password tidak sama")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val response = repositoryAuth.register(
                    state.username,
                    state.password
                )

                if (response.isSuccessful) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isRegisterSuccess = true
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Registrasi gagal"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Kesalahan jaringan"
                    )
                }
            }
        }
    }

    fun resetRegisterStatus() {
        _uiState.update { it.copy(isRegisterSuccess = false) }
    }
}