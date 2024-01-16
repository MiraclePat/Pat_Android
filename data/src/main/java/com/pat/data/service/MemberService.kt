package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.auth.KakaoToken
import com.pat.data.model.member.MyProfileContentDTO
import com.pat.data.model.member.NicknameRequestBody
import com.pat.data.model.member.ParticipatingContentDTO
import com.pat.data.model.member.ParticipatingDetailContentDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface MemberService {
    @GET("/api/v1/members/me")
    suspend fun getMyProfile(): MyProfileContentDTO

    @Multipart
    @PATCH("/api/v1/members/me/profile-image")
    suspend fun updateProfileImage(
        @Part image: MultipartBody.Part,
    ): Response<Unit>

    @PATCH("/api/v1/members/me/profile-nickname")
    suspend fun updateProfileNickname(
        @Body nickname: NicknameRequestBody,
    ): Response<Unit>

    @DELETE("/api/v1/members/me")
    suspend fun deleteMember(): Response<Unit>

    @GET("/api/v1/members/pats")
    suspend fun getParticipatingPats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("state") state: String?,
    ): ListResponse<ParticipatingContentDTO>

    @GET("/api/v1/members/pats/open")
    suspend fun getOpenPats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("state") sort: String?,
    ): ListResponse<ParticipatingContentDTO>

    @GET("/api/v1/members/pats/{pat-id}")
    suspend fun getParticipatingDetail(
        @Path("pat-id") patId: Long,
    ): ParticipatingDetailContentDTO
}
