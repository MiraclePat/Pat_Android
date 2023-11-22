package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.proof.ProofContentDTO
import com.pat.data.service.ProofService
import okhttp3.MultipartBody
import javax.inject.Inject

class ProofDataSource @Inject constructor(
    private val service: ProofService
) {

    suspend fun proofPat(
        repImg: MultipartBody.Part,
    ) {
        return service.proofPat(repImg)
    }

    suspend fun getMyProof(
        patId: Long,
        lastId: Long?,
        size: Int?
    ): ListResponse<ProofContentDTO> {
        return service.getMyProof(patId, lastId, size)
    }

    suspend fun getSomeoneProof(
        patId: Long,
        lastId: Long?,
        size: Int?
    ): ListResponse<ProofContentDTO> {
        return service.getSomeoneProof(patId, lastId, size)
    }
}