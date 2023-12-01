package com.pat.presentation.ui.map

import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onFocusedBoundsChanged
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.OverlayImage
import com.orhanobut.logger.Logger
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBoxList
import com.pat.presentation.ui.common.SelectButton
import com.pat.presentation.ui.home.components.SearchTextField
import com.pat.presentation.ui.map.components.MapCategoryBoxList
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography

@OptIn(ExperimentalNaverMapApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MapScreenView(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
) {
    val initPlace = LatLng(37.4782697, 126.9515261)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(initPlace, 11.0)
    }
    val searchValue = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }


    val mapPats by mapViewModel.mapPats.collectAsState()

    //화면이 움직일 때마다  현재 화면의 좌표값 전달 viewmodel에 전달 0
    //검색하면 viewmodel query를 호출해서 뿌려줍니다 0
    //카테고리를 클릭하면 viewmodel 호출 카테고리 디자인수정  0
    //마커클릭시 마커변경, 바텀으로 올라옴
    //바텀클릭시 detail로 이동
    //카테고리별로 마커달리하기

    //네이버 맵 초기화 ( 관악구청 ) o
    //관악구청 화면의 bound좌표를 viewmodel에 넘겨준다 intit o

    LaunchedEffect(cameraPositionState.isMoving) {
        if (cameraPositionState.isMoving) {
            val northEastCoordinate = cameraPositionState.contentBounds?.northEast
            val southWestCoordinate = cameraPositionState.contentBounds?.southWest
            mapViewModel.getMapPats(northEastCoordinate, southWestCoordinate, category.value)
        }
    }

    Column() {
        SearchTextField(
            state = searchValue,
            text = "팟명으로 검색해보세요.",
            inputEnter = {
                mapViewModel.getMapPats(
                    cameraPositionState.contentBounds?.northEast,
                    cameraPositionState.contentBounds?.southWest,
                    category.value,
                    searchValue.value
                )
            })
        Spacer(modifier = modifier.size(14.dp))
        Row {
            MapCategoryBoxList(
                state = category,
                viewModel = mapViewModel,
                northEastCoordinate = cameraPositionState.contentBounds?.northEast,
                southWestCoordinate = cameraPositionState.contentBounds?.southWest
            )
        }
        Spacer(modifier = modifier.size(14.dp))
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            mapPats.forEach {
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            it.latitude,
                            it.longitude
                        )
                    ),
                    onClick = { clickedMarker ->
                        clickedMarker.icon = OverlayImage.fromResource(R.drawable.ic_chat_check)
                        showBottomSheet = true
                        true
                    },
                    icon = OverlayImage.fromResource(R.drawable.ic_calendar)
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
        ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                }
        }
    }



}







