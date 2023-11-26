package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapPatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import com.pat.data.model.place.ResultGetSearchPlacesDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceService {
    @GET("v1/search/local.json")
    suspend fun getSearchPlaces(
        @Query("query") query: String,
        @Query("display") display: Int?,
        @Query("start") start: Int?,
        @Query("sort") sort: String?,
    ): Response<ResultGetSearchPlacesDTO>
}
