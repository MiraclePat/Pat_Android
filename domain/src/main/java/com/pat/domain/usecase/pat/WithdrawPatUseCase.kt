package com.pat.domain.usecase.pat

import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.repository.PatRepository
import javax.inject.Inject

class WithdrawPatUseCase @Inject constructor(
    private val patRepository: PatRepository,
) {
    suspend operator fun invoke(
        patId: Long,
    ): Result<Unit> =
        patRepository.withdrawPat(patId)
}
