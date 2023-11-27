package com.pat.data.source

import com.pat.data.model.ListResponse
import com.pat.data.model.pat.HomePatContentDTO
import com.pat.data.model.place.ResultGetSearchPlacesDTO
import com.pat.data.service.PlaceService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class PlaceDataSource @Inject constructor(
    private val service: PlaceService,
) {
    suspend fun getSearchPlaces(
        query: String,
        display: Int?,
        start: Int?,
        sort: String?,
        ): Response<ResultGetSearchPlacesDTO> {
        return service.getSearchPlaces(query, display,start,sort)
    }

}
