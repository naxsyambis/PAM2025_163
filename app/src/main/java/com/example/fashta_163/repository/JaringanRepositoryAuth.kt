package com.example.fashta_163.repository

import com.example.fashta_163.apiservice.ServiceApiAuth
import com.example.fashta_163.modeldata.DataAdmin
import retrofit2.Response

class JaringanRepositoryAuth(
    private val serviceApiAuth: ServiceApiAuth
) : RepositoryAuth {

    override suspend fun register(
        username: String,
        password: String
    ): Response<Unit> =
        serviceApiAuth.register(username, password)

    override suspend fun login(
        username: String,
        password: String
    ): Response<Unit> =
        serviceApiAuth.login(username, password)
}