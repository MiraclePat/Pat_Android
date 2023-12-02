package com.pat.presentation.ui.post

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.orhanobut.logger.Logger
import com.pat.domain.model.place.PlaceDetailInfo
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBoxList
import com.pat.presentation.ui.common.CheckBoxView
import com.pat.presentation.ui.common.CustomButtonView
import com.pat.presentation.ui.common.CustomDialog
import com.pat.presentation.ui.common.CustomPicker
import com.pat.presentation.ui.common.CustomTextField
import com.pat.presentation.ui.common.DateTimePickerView
import com.pat.presentation.ui.common.ExampleImageView
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.SelectImageList
import com.pat.presentation.ui.common.WheelTimePickerView
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.navigations.BottomNavItem
import com.pat.presentation.ui.post.components.PostRepImageView
import com.pat.presentation.ui.post.components.SearchPlaceTextField
import com.pat.presentation.ui.post.components.SearchResultList
import com.pat.presentation.ui.post.components.SelectDayButtonList
import com.pat.presentation.ui.theme.Gray100
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.StarColor
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenView(
    navController: NavController,
    onNavigateToHome: () -> Unit,
    viewModel: PostViewModel,
) {

    val scrollState = rememberScrollState()
    val declarationDialogState = remember { mutableStateOf(false) }
    BackHandler {
        declarationDialogState.value = true
    }
    if (declarationDialogState.value) {
        CustomDialog(
            okRequest = {
                viewModel.clearImageData()
                onNavigateToHome()
            }, state = declarationDialogState,
            message = "공고글 작성을 취소하시겠어요?",
            cancelMessage = "계속 작성",
            okMessage = "작성 취소"
        )
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "공고글 작성",
                        fontSize = 14.sp,
                        style = Typography.labelMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        declarationDialogState.value = !declarationDialogState.value
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PostScreenBody(
                navController = navController,
                onNavigateToHome = onNavigateToHome,
                viewModel = viewModel,
            )
        }
    }
}

@Composable
fun PostScreenBody(
    navController: NavController,
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit,
    viewModel: PostViewModel,
) {
    val isRealTime = remember { mutableStateOf(false) }         // 사진 선택

    val title = rememberSaveable { mutableStateOf("") }         // 팟 제목
    val maxPerson = rememberSaveable { mutableStateOf("") }     // 최대 인원
    val patDetail = rememberSaveable { mutableStateOf("") }     // 팟 소개
    val proofDetail = rememberSaveable { mutableStateOf("") }   // 인증 방법 설명
    val startDate = remember { mutableStateOf("") }             // 시작 날짜
    val endDate = remember { mutableStateOf("") }               // 종료 날짜
    val startTime = remember { mutableStateOf("") }             // 시작 시간
    val endTime = remember { mutableStateOf("") }               // 종료 시간
    val category = remember { mutableStateOf("") }              // 카테고리
    val locationSelect = remember { mutableStateOf("") }        // 주소 입력 방식
    val locationSearchValue = remember { mutableStateOf("") }        // 주소 입력 방식
    val onSearchScreen = remember { mutableStateOf(false) }

    val dayList = remember { mutableStateListOf<String>() }                   // 인증 빈도

    val bodyBitmap by viewModel.bodyBitmap.collectAsState() //팟 상세이미지들
    val correctBitmap by viewModel.correctBitmap.collectAsState() //올바른 이미지
    val incorrectBitmap by viewModel.incorrectBitmap.collectAsState() //나쁜이미지!
    val repBitmap by viewModel.repBitmap.collectAsState() //썸네일

    val searchPlaceResult by viewModel.searchPlaceResult.collectAsState() //팟 상세이미지들

    Column {
        PostRepImageView(
            navController = navController,
            bitmap = repBitmap,
            viewModel = viewModel
        )

        Spacer(modifier = modifier.size(20.dp))
        Column(modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "카테고리 선택", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            Row {
                CategoryBoxList(state = category)
            }
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "팟 제목", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(
                placeholderText = "최대 15자",
                state = title,
                maxLength = 15
            )
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "팟 상세정보", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(6.dp))
                Text(
                    text = "최대 5장 가능, 사진1부터 차례대로 표시돼요.",
                    style = Typography.labelMedium,
                    color = Gray400,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = modifier.size(14.dp))
            SelectImageList(
                navController = navController,
                bitmapList = bodyBitmap,
                bitmapType = "BODY",
                viewModel = viewModel
            )
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "위치정보", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(16.dp))
            Column(
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                SelectLocationButtonList(
                    locationState = locationSelect,
                    postViewModel = viewModel,
                    searchValue = locationSearchValue,
                    onSearchScreen = onSearchScreen,
                    searchPlaceResult = searchPlaceResult,
                )
            }
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "팟 인원 설정", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(
                placeholderText = "숫자만 입력해주세요. (최대 10,000명 가능)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                state = maxPerson,
                maxLength = 5,
            )
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "시작일-종료일", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작일 선택",
                    dateState = startDate,
                    formatter = convertDateFormat,
                    widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { startDate.value = it },
                            onDismiss = { startPressed.value = !startPressed.value },
                        )
                    },
                    clickState = startPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("부터 시작", style = Typography.bodySmall)
                Spacer(modifier = modifier.padding(10.dp))
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료일 선택",
                    dateState = endDate,
                    formatter = convertDateFormat,
                    widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { endDate.value = it },
                            onDismiss = { endPressed.value = !endPressed.value },
                        )
                    },
                    clickState = endPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("에 종료", style = Typography.bodySmall)
            }
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "인증 가능 시간", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작시간",
                    dateState = startTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            startPressed.value = !startPressed.value
                        }, timeState = startTime)
                    },
                    clickState = startPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("부터", style = Typography.bodySmall)
                Spacer(modifier = modifier.padding(10.dp))
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료시간",
                    dateState = endTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            endPressed.value = !endPressed.value
                        }, timeState = endTime)
                    },
                    clickState = endPressed
                )
                Spacer(modifier = modifier.padding(8.dp))
                Text("까지", style = Typography.bodySmall)
            }
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "팟 소개", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(
                placeholderText = "최소 15자, 최대 500자",
                state = patDetail,
                maxLength = 500,
            )
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "인증 빈도", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
                Spacer(modifier = modifier.size(6.dp))
                Text(
                    text = "다중선택 가능해요",
                    style = Typography.labelMedium,
                    color = Gray400,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = modifier.size(14.dp))
            Row() {
                SelectDayButtonList(state = dayList)
            }
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "인증방법 설명", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            CustomTextField(
                placeholderText = "최소 5자, 최대 30자",
                maxLength = 30,
                state = proofDetail
            )
            Spacer(modifier = modifier.size(36.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "인증사진 예시", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
            }
            Spacer(modifier = modifier.size(14.dp))
            Row() {
                ExampleImageView(
                    navController = navController,
                    text = "올바른 예시",
                    backColor = GreenBack,
                    textColor = GreenText,
                    bitmap = correctBitmap,
                    bitmapType = "CORRECT",
                    viewModel = viewModel
                )
                Spacer(modifier = modifier.size(10.dp))
                ExampleImageView(
                    navController = navController,
                    text = "잘못된 예시",
                    backColor = RedBack,
                    textColor = RedText,
                    bitmap = incorrectBitmap,
                    bitmapType = "INCORRECT",
                    viewModel = viewModel
                )
            }
            Spacer(modifier = modifier.size(36.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "인증 수단", style = Typography.titleLarge)
                Spacer(modifier = modifier.size(4.dp))
                Text(text = "*", style = Typography.titleLarge, color = StarColor)
                Spacer(modifier = modifier.size(6.dp))
                Text(
                    text = "실시간 촬영은 필수에요.",
                    style = Typography.labelMedium,
                    color = Gray400,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = modifier.size(14.dp))
            Row {
                CheckBoxView(text = "실시간 촬영")
                Spacer(modifier = modifier.size(12.dp))
                CheckBoxView(checked = isRealTime, text = "갤러리에서 사진 가져오기", isRealTime = true)
            }
            Spacer(modifier = modifier.size(55.dp))

            FinalButton(text = "확정",
                backColor = PrimaryMain,
                textColor = White,
                onClick = {
                    val outputStartTime = convertTimeFormat(startTime.value)
                    val outputEndTime = convertTimeFormat(endTime.value)
                    Logger.t("MainTest").i("$outputStartTime, ${outputEndTime}")
                    viewModel.post(
                        patName = title.value,
                        maxPerson = maxPerson.value.toInt(),
                        patDetail = patDetail.value,
                        proofDetail = proofDetail.value,
                        startDate = startDate.value,
                        endDate = endDate.value,
                        startTime = outputStartTime,
                        endTime = outputEndTime,
                        days = dayList.toList(),
                        category = category.value,
                        realtime = !isRealTime.value,
                    )
//                    onNavigateToHome()
                    navController.navigate(BottomNavItem.Home.screenRoute)
                })
        }
    }
}

@Composable
fun SelectDayButtonList(state: SnapshotStateList<String>) {
    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")

    @Composable
    fun dayButtonView(day: String) {
        CustomButtonView(
            modifier = Modifier.requiredHeight(32.dp),
            text = day,
            onClick = {
                if (state.contains(day)) {
                    state.remove(day)
                } else {
                    state.add(day)
                }
            },
            isSelected = state.contains(day),
            shape = RoundedCornerShape(22.dp),
            fontSize = 13.sp,
            borderColor = Gray300,
            textColor = Gray500
        )
        Spacer(Modifier.size(10.dp))
    }

    Column {
        Row() {
            days.take(5).forEach { day ->
                dayButtonView(day)
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row() {
            days.takeLast(2).forEach { day ->
                dayButtonView(day)
            }
        }
    }
}

@Composable
fun SelectLocationButtonList(
    modifier: Modifier = Modifier,
    locationState: MutableState<String>,
    onClick: () -> Unit = {},
    postViewModel: PostViewModel,
    searchValue: MutableState<String>,
    onSearchScreen: MutableState<Boolean>,
    searchPlaceResult: List<PlaceDetailInfo>
) {

    val locationButtonText = listOf<String>("주소 검색", "위치정보 없음")

    @Composable
    fun locationButtonView(modifier: Modifier, location: String) {
        CustomButtonView(
            modifier = modifier,
            text = location,
            onClick = {
                onClick()
                locationState.value = location
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
            SearchPlaceTextField(placeholderText = "서초동 스타벅스",
                maxLength = 30,
                state = searchValue,
                onScreen = onSearchScreen,
                viewModel = postViewModel,
                maxLines = 1,
                inputEnter = {
                    //TODO NOT WORKING
                    postViewModel.onSearch(searchValue.value)
                })
            Spacer(modifier.padding(bottom = 24.dp))
            Text(
                text = "아래 검색결과 중에서 선택해주세요!",
                style = Typography.labelMedium,
                fontSize = 13.sp,
                color = PrimaryMain
            )

            if (onSearchScreen.value) {
                SearchResultList(
                    places = searchPlaceResult,
                    placeText = searchValue,
                    postViewModel = postViewModel
                )
            }
        }

        "위치정보 없음" -> Box(contentAlignment = Alignment.Center) {
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
