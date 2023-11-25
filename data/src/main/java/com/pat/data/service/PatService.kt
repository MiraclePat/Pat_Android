package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapPatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PatService {
    @GET("/api/test/pats/home")
    suspend fun getHomePats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
        @Query("query") query: String?,
        @Query("category") category: String?,
        @Query("showFull") showFull: Boolean?,
        @Query("state") state: String?,
    ): ListResponse<HomePatContentDTO>

    @GET("/api/test/pats/map")
    suspend fun getMapPats(
        @Query("lastId") lastId: Long?,
        @Query("size") size: Int?,
        @Query("query") query: String?,
        @Query("category") category: String?,
        @Query("leftLongitude") leftLongitude: Double?,
        @Query("rightLongitude") rightLongitude: Double?,
        @Query("bottomLatitude") bottomLatitude: Double?,
        @Query("topLatitude") topLatitude: Double?,
    ): ListResponse<MapPatContentDTO>

    @GET("/api/test/pats/{pat-id}")
    suspend fun getPatDetail(
        @Path("pat-id") patId: Long,
    ): PatDetailContentDTO

    @Multipart
    @POST("/api/test/pats")
    suspend fun createPat(
        @Part repImg: MultipartBody.Part,
        @Part correctImg: MultipartBody.Part,
        @Part incorrectImg: List<MultipartBody.Part>,
        @Part bodyImg: List<MultipartBody.Part>,
        @Part pat: MultipartBody.Part,
    )

    @Multipart
    @PATCH("/api/test/pats/{pat-id}")
    suspend fun updatePat(
        @Path("pat-id") patId: Long,
        @Part repImg: MultipartBody.Part,
        @Part correctImg: MultipartBody.Part,
        @Part incorrectImg: List<MultipartBody.Part>,
        @Part bodyImg: List<MultipartBody.Part>,
        @Part pat: MultipartBody.Part,
    ): Response<Unit>

    @POST("/api/test/pats/{pat-id}")
    suspend fun participatePat(
        @Path("pat-id") patId: Long,
    ): Response<Unit>

    @DELETE("/api/test/pats/{pat-id}")
    suspend fun deletePat(
        @Path("pat-id") patId: Long,
    ): Response<Unit>

    @DELETE("/api/test/pats/{pat-id}/withdraw")
    suspend fun withdrawPat(
        @Path("pat-id") patId: Long,
    ): Response<Unit>
}
