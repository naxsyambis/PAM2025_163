package com.example.fashta_163.viewmodel.provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fashta_163.repository.RepositoryAuth
import com.example.fashta_163.viewmodel.LoginViewModel
import com.example.fashta_163.viewmodel.RegisterViewModel

object PenyediaViewModel {

    fun provideLoginViewModelFactory(
        repositoryAuth: RepositoryAuth
    ): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return LoginViewModel(repositoryAuth) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    fun provideRegisterViewModelFactory(
        repositoryAuth: RepositoryAuth
    ): ViewModelProvider.Factory =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return RegisterViewModel(repositoryAuth) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
}
