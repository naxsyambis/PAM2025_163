package com.example.fashta_163.modeldata.ui

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val errorMessage: String? = null
)