package com.pat.data.repository.place

import com.orhanobut.logger.Logger
import com.pat.data.repository.image.ImageRepositoryImpl
import com.pat.data.source.ImageDataSource
import com.pat.data.source.PatDataSource
import com.pat.data.source.PlaceDataSource
import com.pat.data.util.exception
import com.pat.domain.model.exception.UnKnownException
import com.pat.domain.model.pat.CreatePatDetail
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.HomePatRequestInfo
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.model.pat.PatDetailContent
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.repository.PatRepository
import com.pat.domain.repository.PlaceRepository
import com.squareup.moshi.Moshi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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
