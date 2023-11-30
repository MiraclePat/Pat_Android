package com.pat.presentation.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberMarkerState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreenView() {
    val scrollState = rememberScrollState()


//    ProofScreenView()
    NaverMap(
        modifier = Modifier.fillMaxSize()
    ){
        Marker(state = rememberMarkerState(position = BOUNDS_1.northEast))
        Marker(state = rememberMarkerState(position = BOUNDS_1.southWest))
        Marker(
            state = MarkerState(position = LatLng(37.532600, 127.024612)),
            captionText = "Marker in Seoul"
        )
    }
}

private val BOUNDS_1 = LatLngBounds(
    LatLng(37.4282975, 126.7644840),
    LatLng(37.7014553, 127.1837949)
)

