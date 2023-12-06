package com.pat.presentation.ui.pat.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orhanobut.logger.Logger
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.presentation.ui.common.CustomButtonView
import com.pat.presentation.ui.pat.PatUpdateViewModel
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun UpdateSelectLocationButtonList(
    modifier: Modifier = Modifier,
    locationState: MutableState<String>,
    viewModel: PatUpdateViewModel,
    searchValue: MutableState<String>,
    onSearchScreen: MutableState<Boolean>,
    searchPlaceResult: List<PlaceDetailInfo>,
    scrollState: ScrollState,
    scrollToPosition: Float,
    placeSelected: MutableState<Boolean>
) {
    val locationButtonText = listOf<String>("주소 검색", "위치정보 없음")
    val coroutineScope = rememberCoroutineScope()

    @Composable
    fun locationButtonView(modifier: Modifier, location: String) {
        CustomButtonView(
            modifier = modifier,
            text = location,
            onClick = {
                if (locationState.value != location) {
                    locationState.value = location
                    if (locationState.value == "주소 검색") {
                        coroutineScope.launch {
                            scrollState.scrollTo(scrollToPosition.roundToInt())
                        }
                    }
                }
            },
            isSelected = locationState.value == location,
            shape = RoundedCornerShape(100.dp),
            fontSize = 14.sp,
            borderColor = Gray300,
            textColor = Gray600
        )
        Spacer(Modifier.size(10.dp))
    }

    Row(modifier.fillMaxWidth()) {
        locationButtonText.forEach { location ->
            locationButtonView(modifier.weight(1f), location)
        }
    }
    Spacer(modifier.padding(top = 16.dp))
    when (locationState.value) {
        "주소 검색" -> {
            UpdateSearchPlaceTextField(placeholderText = "서초동 스타벅스",
                maxLength = 30,
                state = searchValue,
                onScreen = onSearchScreen,
                viewModel = viewModel,
                maxLines = 1,
                placeSelected = placeSelected,
                inputEnter = {
                    viewModel.onSearch(searchValue.value)
                })
            Spacer(modifier.padding(bottom = 24.dp))
            Text(
                text = "아래 검색결과 중에서 선택해주세요!",
                style = Typography.labelMedium,
                fontSize = 13.sp,
                color = PrimaryMain
            )

            if (onSearchScreen.value) {
                UpdateSearchResultList(
                    places = searchPlaceResult,
                    placeText = searchValue,
                    viewModel = viewModel,
                    placeSelected = placeSelected
                )
            }
        }

        "위치정보 없음" -> Box(contentAlignment = Alignment.Center) {
            viewModel.selectPlace()
            Text("위치정보 없음 선택 시 지도에 나타나지 않아요.", style = Typography.bodySmall)
        }

        else -> {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier
                        .width(42.dp)
                        .height(26.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(Primary50),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tip!",
                        style = Typography.labelMedium,
                        fontSize = 12.sp,
                        color = PrimaryMain
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .weight(1f),
                    text = "실 주소 입력시 정확한 위치 파악이 가능해요!",
                    style = Typography.labelMedium,
                    fontSize = 13.sp,
                    color = PrimaryMain
                )
            }
        }
    }
}