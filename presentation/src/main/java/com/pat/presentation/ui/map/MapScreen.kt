package com.pat.presentation.ui.map

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.MapPatContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.home.components.SearchTextField
import com.pat.presentation.ui.map.components.MapCategoryBoxList
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreenView(
    modifier: Modifier = Modifier,
    mapViewModel: MapViewModel = hiltViewModel(),
    navController: NavController
) {
    val initPlace = LatLng(37.4782697, 126.9515261)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(initPlace, 11.0)
    }
    val searchValue = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("전체보기") }
    val showBottomSheet = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val mapPats by mapViewModel.mapPats.collectAsState()
//    var selectedPat by remember { mutableStateOf<MapPatContent>(mapPats.first()) }

    //화면이 움직일 때마다  현재 화면의 좌표값 전달 viewmodel에 전달 0
    //검색하면 viewmodel query를 호출해서 뿌려줍니다 0
    //카테고리를 클릭하면 viewmodel 호출 카테고리 디자인수정  0
    //마커클릭시 마커변경, 바텀으로 올라옴 o
    //바텀클릭시 detail로 이동 o
    //카테고리별로 마커달리하기 o

    //네이버 맵 초기화 ( 관악구청 ) o
    //관악구청 화면의 bound좌표를 viewmodel에 넘겨준다 intit o

    LaunchedEffect(cameraPositionState.isMoving) {
        if (cameraPositionState.isMoving) {
            val northEastCoordinate = cameraPositionState.contentBounds?.northEast
            val southWestCoordinate = cameraPositionState.contentBounds?.southWest
            mapViewModel.getMapPats(northEastCoordinate, southWestCoordinate, category.value)
        }
    }

    val category_daily = stringResource(id = R.string.category_daily)
    val category_etc = stringResource(id = R.string.category_etc)
    val category_habit = stringResource(id = R.string.category_habit)
    val category_health = stringResource(id = R.string.category_health)
    val category_environment = stringResource(id = R.string.category_environment)
    val category_hobby = stringResource(id = R.string.category_hobby)

    fun mapSelectIcon(category: String): Int {
        return when (category) {
            category_daily -> R.drawable.map_selected_daily
            category_etc -> R.drawable.map_selected_etc
            category_habit -> R.drawable.map_selected_habbit
            category_health -> R.drawable.map_selected_health
            category_environment -> R.drawable.map_selected_environment
            category_hobby -> R.drawable.map_selected_hobby
            else -> {
                R.drawable.ic_map
            }
        }
    }

    fun mapIcon(category: String): Int {
        return when (category) {
            category_daily -> R.drawable.map_daily
            category_etc -> R.drawable.map_etc
            category_habit -> R.drawable.map_habbit
            category_health -> R.drawable.map_health
            category_environment -> R.drawable.map_environment
            category_hobby -> R.drawable.map_hobby
            else -> {
                R.drawable.ic_map
            }
        }
    }

    Column() {
        Column(modifier.padding(top = 26.dp, bottom = 8.dp, start = 18.dp, end = 18.dp)) {
            SearchTextField(
                modifier = modifier.height(46.dp),
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
            Row(modifier.horizontalScroll(scrollState)) {
                MapCategoryBoxList(
                    state = category,
                    viewModel = mapViewModel,
                    northEastCoordinate = cameraPositionState.contentBounds?.northEast,
                    southWestCoordinate = cameraPositionState.contentBounds?.southWest
                )
            }
        }
        Spacer(modifier = modifier.size(14.dp))
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            mapPats.forEach { content ->
                val focused = remember { mutableStateOf(false) }
                val icon =
                    if (focused.value) mapSelectIcon(content.category) else mapIcon(content.category)
                if (focused.value) {
                    MapBottomSheet(
                        showBottomSheet = showBottomSheet,
                        focused = focused,
                        navController = navController,
                        content = content
                    )
                }
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            content.latitude,
                            content.longitude
                        )
                    ),
                    onClick = { clickedMarker ->
                        focused.value = true
                        showBottomSheet.value = true
                        false
                    },
                    icon = OverlayImage.fromResource(
                        icon
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: MutableState<Boolean>,
    focused: MutableState<Boolean>,
    navController: NavController,
    content: MapPatContent
) {
    if (showBottomSheet.value) {
        ModalBottomSheet(
            modifier = modifier
                .fillMaxWidth(),
            containerColor = White,
            onDismissRequest = {
                showBottomSheet.value = false
                focused.value = false
            },
        ) {
            Row(
                modifier
                    .padding(start = 22.dp, end = 22.dp, top = 10.dp, bottom = 40.dp)
                    .clickable {
                        navController.navigate("patDetail/${content.patId}")
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier) {
                    Row() {
                        CategoryBox(
                            category = content.category,
                            isSelected = true
                        )
                        Spacer(modifier.size(8.dp))
                        Text(
                            text = content.patName,
                            style = Typography.labelMedium,
                            color = Color.Black
                        )
                    }
                    Spacer(modifier.size(12.dp))
                    SimpleTextView(
                        modifier = modifier.padding(bottom = 6.dp),
                        text = content.location.ifEmpty { "어디서나 가능" },
                        vectorResource = R.drawable.ic_map,
                        iconColor = Gray400
                    )
                    SimpleTextView(
                        modifier = modifier.padding(bottom = 6.dp),
                        text = "${content.startDate} 시작",
                        vectorResource = R.drawable.ic_calendar,
                        iconColor = Gray400
                    )
                    SimpleTextView(
                        modifier = modifier.padding(bottom = 6.dp),
                        text = "${content.days} 인증",
                        vectorResource = R.drawable.ic_chat_check,
                        iconColor = Gray400
                    )
                    SimpleTextView(
                        modifier = modifier.padding(bottom = 6.dp),
                        text = "${content.nowPerson}명 / ${content.maxPerson}명",
                        vectorResource = R.drawable.ic_user,
                        iconColor = Gray400
                    )
                }
                Spacer(modifier = modifier.weight(1f))
                GlideImage(modifier = modifier.size(120.dp), imageModel = { content.repImg })
            }
        }
    }
}

