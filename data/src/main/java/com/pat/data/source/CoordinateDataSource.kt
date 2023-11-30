package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.place.CoordinateResponseDTO
import com.pat.data.model.place.ResultGetSearchPlacesDTO
import com.pat.data.service.CoordinateService
import com.pat.data.service.PlaceService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class CoordinateDataSource @Inject constructor(
    private val service: CoordinateService,
) {
    suspend fun getSearchCoordinate(
        query: String,
        ): Response<CoordinateResponseDTO> {
        return service.getSearchCoordinate(query)
    }

}
