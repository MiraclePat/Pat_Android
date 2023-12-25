package com.pat.domain.usecase.auth

import com.pat.domain.repository.AuthRepository
import javax.inject.Inject

class SetUserKeyUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(key: String): Result<Unit> =
        authRepository.setUserKey(key)
}