package com.pat.domain.usecase.auth

import com.pat.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserKeyUseCase @Inject constructor(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Result<String?> =
        authRepository.getUserKey()
}