package com.example.fashta_163.repository

import com.example.fashta_163.modeldata.DataAdmin
import retrofit2.Response

interface RepositoryAuth {

    suspend fun login(
        username: String,
        password: String
    ): Response<Unit>

    suspend fun register(
        username: String,
        password: String
    ): Response<Unit>
}
