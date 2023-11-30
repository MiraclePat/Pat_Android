package com.pat.data.repository.place

import com.orhanobut.logger.Logger
import com.pat.data.model.place.AddressDTO
import com.pat.data.source.CoordinateDataSource
import com.pat.data.source.PlaceDataSource
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.place.CoordinateInfo
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.repository.CoordinateRepository
import com.pat.domain.repository.PlaceRepository
import javax.inject.Inject

class CoordinateRepositoryImpl @Inject constructor(
    private val coordinateDataSource: CoordinateDataSource,
    ) : CoordinateRepository {

    override suspend fun getSearchCoordinate(query: String): Result<CoordinateInfo> {
        val response =
            coordinateDataSource.getSearchCoordinate(
                query
            )

        return if (response.isSuccessful) {
            Result.success(response.body()!!.addresses?.first()!!.toCoordinateInfo())
        } else {
            Logger.t("MainTest").i("${response.errorBody()}")
            Result.failure(UnKnownException())
        }    }

    fun AddressDTO.toCoordinateInfo() : CoordinateInfo {
        return CoordinateInfo(x!!.toDouble(),y!!.toDouble())
    }
}
