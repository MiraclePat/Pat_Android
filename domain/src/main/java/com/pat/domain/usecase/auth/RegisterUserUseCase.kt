package com.pat.domain.usecase.auth

import com.pat.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        userCode: String,
    ): Result<Unit> =
        authRepository.register(userCode)
}
