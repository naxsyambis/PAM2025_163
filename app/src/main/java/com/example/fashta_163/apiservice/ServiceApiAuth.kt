package com.example.fashta_163.apiservice

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServiceApiAuth {

    @FormUrlEncoded
    @POST("auth/register.php")
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<Unit>

    @FormUrlEncoded
    @POST("auth/login.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<Unit>
}
