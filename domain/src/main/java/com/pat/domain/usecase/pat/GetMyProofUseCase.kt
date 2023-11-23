package com.pat.domain.usecase.pat

import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.repository.ProofRepository
import javax.inject.Inject

class GetMyProofUseCase @Inject constructor(
    private val proofRepository: ProofRepository,
) {
    suspend operator fun invoke(
        patId: Long,
        proofRequestInfo: ProofRequestInfo,
    ): Result<List<ProofContent>> =
        proofRepository.getMyProofs(patId, proofRequestInfo)
}
