package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapPatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import com.pat.data.service.PatService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class PatDataSource @Inject constructor(
    private val service: PatService,
) {
    suspend fun getHomePats(
        lastId: Long?,
        size: Int?,
        sort: String?,
        query: String?,
        category: String?,
        showFull: Boolean?,
        state: String?,
        ): ListResponse<HomePatContentDTO> {
        return service.getHomePats(lastId, size, sort, query, category,showFull,state)
    }

    suspend fun getMapPats(
        lastId: Long?,
        size: Int?,
        query: String?,
        category: String?,
        leftLongitude: Double?,
        rightLongitude: Double?,
        bottomLatitude: Double?,
        topLatitude: Double?,
    ): ListResponse<MapPatContentDTO> {
        return service.getMapPats(lastId, size, query, category, leftLongitude,rightLongitude,bottomLatitude,topLatitude)
    }

    suspend fun getPatDetail(patId: Long
    ): PatDetailContentDTO {
        return service.getPatDetail(patId)
    }

    suspend fun createPat(
        repImg : MultipartBody.Part,
        correctImg : MultipartBody.Part,
        incorrectImg: MultipartBody.Part,
        bodyImg: List<MultipartBody.Part>,
        pat : MultipartBody.Part,
    ) {
        return service.createPat(repImg,correctImg,incorrectImg,bodyImg,pat)
    }

    suspend fun updatePat(
        patId: Long,
        repImg : MultipartBody.Part,
        correctImg : MultipartBody.Part,
        incorrectImg: MultipartBody.Part,
        bodyImg: List<MultipartBody.Part>,
        pat : MultipartBody.Part,
    ): Response<Unit> {
        return service.updatePat(patId,repImg,correctImg,incorrectImg,bodyImg,pat)
    }

    suspend fun deletePat(
        patId: Long,
    ): Response<Unit> {
        return service.deletePat(patId)
    }

    suspend fun withdrawPat(
        patId: Long,
    ): Response<Unit> {
        return service.withdrawPat(patId)
    }

    suspend fun participatePat(
        patId: Long,
    ): Response<Unit> {
        return service.participatePat(patId)
    }

}
