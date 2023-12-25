package com.pat.domain.usecase.auth

import com.pat.domain.repository.AuthRepository
import javax.inject.Inject

class AutoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(
        customToken: String
    ): Result<Unit> =
        authRepository.loginWithCustomToken(customToken)
}
