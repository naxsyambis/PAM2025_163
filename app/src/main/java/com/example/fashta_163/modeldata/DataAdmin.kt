package com.example.fashta_163.modeldata


import kotlinx.serialization.Serializable

@Serializable
data class DataAdmin(
    val id: Int = 0,
    val username: String = "",
    val password: String = "" // hanya dikirim saat login/register
)
