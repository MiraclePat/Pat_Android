package com.pat.data.service

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("/api/v1/auth/login")
    suspend fun login(): Response<Unit>
}