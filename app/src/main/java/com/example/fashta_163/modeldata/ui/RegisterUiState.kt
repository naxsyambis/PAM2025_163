package com.example.fashta_163.modeldata.ui

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val errorMessage: String? = null
)