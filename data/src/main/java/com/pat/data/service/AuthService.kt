package com.pat.data.service

import com.pat.data.model.auth.FirebaseTokenDTO
import com.pat.data.model.auth.KakaoCode
import com.pat.data.model.auth.KakaoToken
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body token: KakaoToken,
    ): FirebaseTokenDTO

    @POST("/api/v1/auth/signup")
    suspend fun register(
        @Body id: KakaoCode,
    )
}