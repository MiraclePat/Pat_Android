package com.pat.presentation.ui.map

import android.graphics.Bitmap
import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.CreatePatInfo
import com.pat.domain.model.pat.CreatePatInfoDetail
import com.pat.domain.model.pat.HomePatContent
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.domain.model.place.PlaceSearchRequestInfo
import com.pat.domain.usecase.image.GetByteArrayByUriUseCase
import com.pat.domain.usecase.pat.CreatePatUseCase
import com.pat.domain.usecase.pat.GetMapPatsUseCase
import com.pat.domain.usecase.place.GetSearchCoordinateUseCase
import com.pat.domain.usecase.place.GetSearchPlaceUseCase
import com.pat.presentation.model.PatBitmap
import com.pat.presentation.util.image.byteArrayToBitmap
import com.pat.presentation.util.image.getCompressedBytes
import com.pat.presentation.util.image.getRotatedBitmap
import com.pat.presentation.util.image.getScaledBitmap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getMapPatsUseCase: GetMapPatsUseCase,
) : ViewModel() {

    private val _mapPats = MutableStateFlow<List<MapPatContent>>(emptyList())
    val mapPats = _mapPats.asStateFlow()

    init {
        getMapPats(
            northEastCoordinate = LatLng(37.56594885934854, 127.0174440699783),
            southWestCoordinate = LatLng(37.39048754490495, 126.88560813002232)
        )
    }

    fun getMapPats(
        northEastCoordinate: LatLng?,
        southWestCoordinate: LatLng?,
        category: String? = null,
        query: String? = null
    ) {
        viewModelScope.launch {
            val categoryValue =
                if (category == "전체보기") {
                    ""
                } else {
                    category
                }

            // state가 null이면 전체, SCHEDULED , IN_PROGRESS ,COMPLETED
            val result = getMapPatsUseCase(
                MapPatRequestInfo(
                    state = "SCHEDULED",
                    size = null,
                    query = query,
                    category = categoryValue,
                    leftLongitude = southWestCoordinate?.longitude,
                    rightLongitude = northEastCoordinate?.longitude,
                    topLatitude = northEastCoordinate?.latitude,
                    bottomLatitude = southWestCoordinate?.latitude,
                )
            )
            if (result.isSuccess) {
                _mapPats.emit(result.getOrThrow())
            } else {
                Logger.t("navermap").i("${result.exceptionOrNull()}")
            }
        }
    }


}