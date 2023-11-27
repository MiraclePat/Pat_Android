package com.pat.domain.usecase.proof

import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo
import com.pat.domain.repository.ProofRepository
import javax.inject.Inject

class GetSomeoneProofUseCase @Inject constructor(
    private val proofRepository: ProofRepository,
) {
    suspend operator fun invoke(
        patId: Long, proofRequestInfo: ProofRequestInfo,
    ): Result<List<ProofContent>> =
        proofRepository.getSomeoneProofs(patId, proofRequestInfo)
}
