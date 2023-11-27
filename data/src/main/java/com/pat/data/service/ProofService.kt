package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.proof.ProofContentDTO
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ProofService {
    @Multipart
    @POST("/api/test/members/pats/{pat-id}/proofs")
    suspend fun proofPat(
        @Path("pat-id") patId: Long,
        @Part proofImg : MultipartBody.Part,
    )

    @GET("/api/test/members/pats/{pat-id}/proofs")
    suspend fun getMyProof(
        @Path("pat-id") patId: Long,
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
    ): ListResponse<ProofContentDTO>

    @GET("/api/test/members/pats/{pat-id}/proofs/another")
    suspend fun getSomeoneProof(
        @Path("pat-id") patId: Long,
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
    ): ListResponse<ProofContentDTO>
}