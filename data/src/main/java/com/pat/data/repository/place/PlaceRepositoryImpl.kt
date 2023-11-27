package com.pat.data.repository.place

import com.orhanobut.logger.Logger
import com.pat.data.source.PlaceDataSource
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val placeDataSource: PlaceDataSource,
    ) : PlaceRepository {

    override suspend fun getSearchPlace(placeSearchRequestInfo: PlaceSearchRequestInfo): Result<List<PlaceDetailInfo>> {
        val response =
            placeDataSource.getSearchPlaces(
                placeSearchRequestInfo.query,
                placeSearchRequestInfo.display,
                placeSearchRequestInfo.start,
                placeSearchRequestInfo.sort
                )

        return if (response.isSuccessful) {
            Result.success(response.body()!!.places)
        } else {
            Logger.t("MainTest").i("${response.errorBody()}")
            Result.failure(UnKnownException())
        }
    }

}
