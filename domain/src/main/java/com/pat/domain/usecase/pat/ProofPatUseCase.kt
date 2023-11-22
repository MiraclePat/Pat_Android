package com.pat.domain.usecase.pat

import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.repository.ProofRepository
import javax.inject.Inject

class ProofPatUseCase @Inject constructor(
    private val proofRepository: ProofRepository,
) {
    suspend operator fun invoke(
        patId: Long,
        proofPatInfo: ProofPatInfo,
    ): Result<Unit> =
        proofRepository.proofPat(patId, proofPatInfo)
}