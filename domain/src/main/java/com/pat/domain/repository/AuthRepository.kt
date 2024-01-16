package com.pat.domain.repository

interface AuthRepository {
    suspend fun logout(): Result<Unit>

    suspend fun register(
        userCode: String,
    ): Result<Unit>

    suspend fun login(): Result<Unit>
    suspend fun loginWithCustomToken(firebaseToken: String): Result<Unit>

    suspend fun getUsercode(): Result<String?>
    suspend fun getUserKey(): Result<String?>
    suspend fun setUserKey(key: String): Result<Unit>
    suspend fun getLoginStatus(): Result<Boolean?>


}