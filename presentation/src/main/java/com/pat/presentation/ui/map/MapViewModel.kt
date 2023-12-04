package com.pat.presentation.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.MapPatContent
import com.pat.domain.model.pat.MapPatRequestInfo
import com.pat.domain.usecase.pat.GetMapPatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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

            val mapRequestInfo = MapPatRequestInfo(
                state = "SCHEDULED",
                size = null,
                query = query,
                category = categoryValue,
                leftLongitude = southWestCoordinate?.longitude,
                rightLongitude = northEastCoordinate?.longitude,
                bottomLatitude = southWestCoordinate?.latitude,
                topLatitude = northEastCoordinate?.latitude,
            )
            val result = getMapPatsUseCase(
                mapRequestInfo
            )
            if (result.isSuccess) {
                _mapPats.emit(result.getOrThrow())
            } else {
                Logger.t("navermap").i("${result.exceptionOrNull()}")
            }
        }
    }


}