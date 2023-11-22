package com.pat.domain.repository

import com.pat.domain.model.proof.ProofPatInfo
import com.pat.domain.model.proof.ProofContent
import com.pat.domain.model.proof.ProofRequestInfo

interface ProofRepository {
    suspend fun proofPat(
        patId: Long,
        proofPatInfo: ProofPatInfo
    ): Result<Unit>

    suspend fun getMyProofs(
        patId: Long,
        proofRequestInfo: ProofRequestInfo,
    ): Result<List<ProofContent>>

    suspend fun getSomeoneProofs(
        patId: Long,
        proofRequestInfo: ProofRequestInfo,
    ): Result<List<ProofContent>>

}