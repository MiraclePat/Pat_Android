package com.pat.data.service

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.pat.MapPatContentDTO
import com.pat.data.model.pat.PatDetailContentDTO
import com.pat.data.model.place.CoordinateResponseDTO
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

interface CoordinateService {
    @GET("map-geocode/v2/geocode")
    suspend fun getSearchCoordinate(
        @Query("query") query: String,
    ): Response<CoordinateResponseDTO>


}
