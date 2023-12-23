package com.pat.presentation.ui.post

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
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
import com.pat.presentation.ui.common.SelectDayButtonList
import com.pat.presentation.ui.common.SelectImageList
import com.pat.presentation.ui.common.SnackBar
import com.pat.presentation.ui.common.WheelTimePickerView
import com.pat.presentation.ui.common.convertDateFormat
import com.pat.presentation.ui.common.convertTimeFormat
import com.pat.presentation.ui.login.Event
import com.pat.presentation.ui.post.components.PostRepImageView
import com.pat.presentation.ui.post.components.PostTitleBox
import com.pat.presentation.ui.post.components.SearchPlaceTextField
import com.pat.presentation.ui.post.components.SearchResultList
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.StarColor
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.pat.presentation.util.HOME
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreenView(
    navController: NavController,
    viewModel: PostViewModel,
) {
    val scrollState = rememberScrollState()
    val declarationDialogState = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    BackHandler {
        declarationDialogState.value = true
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                is PostEvent.PostSuccess -> {
                    viewModel.clearImageData()
                    navController.popBackStack(
                        route = HOME,
                        inclusive = false
                    )
                }

                is PostEvent.PostFailed -> {
                    errorMessage.value = "등록 실패"
                }

                is PostEvent.SearchPlaceSuccess -> {}

                is PostEvent.SearchPlaceFailed -> {
                    errorMessage.value = "검색 실패"
                }

                is PostEvent.SearchCoordinateSuccess -> {}

                is PostEvent.SearchCoordinateFailed -> {
                    errorMessage.value = "좌표 검색 실패"
                }
            }
        }
    }

    if (declarationDialogState.value) {
        CustomDialog(
            okRequest = {
                viewModel.clearImageData()
                navController.popBackStack(
                    route = HOME,
                    inclusive = false
                )
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
                viewModel = viewModel,
                scrollState = scrollState
            )
        }

        if (errorMessage.value.isNotEmpty()) {
            SnackBar(errorMessage)
        }
    }
}

@Composable
fun PostScreenBody(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PostViewModel,
    scrollState: ScrollState
) {
    val isRealTime = rememberSaveable { mutableStateOf(false) }         // 사진 선택
    val title = rememberSaveable { mutableStateOf("") }         // 팟 제목
    val maxPerson = rememberSaveable { mutableStateOf("") }     // 최대 인원
    val patDetail = rememberSaveable { mutableStateOf("") }     // 팟 소개
    val proofDetail = rememberSaveable { mutableStateOf("") }   // 인증 방법 설명
    val startDate = rememberSaveable { mutableStateOf("") }             // 시작 날짜
    val endDate = rememberSaveable { mutableStateOf("") }               // 종료 날짜
    val startTime = rememberSaveable { mutableStateOf("") }             // 시작 시간
    val endTime = rememberSaveable { mutableStateOf("") }               // 종료 시간
    val category = rememberSaveable { mutableStateOf("") }              // 카테고리
    val locationSelect = rememberSaveable { mutableStateOf("") }        // 주소 입력 방식
    val locationSearchValue = rememberSaveable { mutableStateOf("") }        // 주소 입력 방식
    val onSearchScreen = rememberSaveable { mutableStateOf(false) }
    val placeSelected = rememberSaveable { mutableStateOf(false) }
    val dayList = rememberSaveable { mutableStateOf(listOf<String>()) }                   // 인증 빈도

    val bodyBitmap by viewModel.bodyBitmap.collectAsState() //팟 상세이미지들
    val correctBitmap by viewModel.correctBitmap.collectAsState() //올바른 이미지
    val incorrectBitmap by viewModel.incorrectBitmap.collectAsState() //나쁜이미지!
    val repBitmap by viewModel.repBitmap.collectAsState() //썸네일

    val searchPlaceResult by viewModel.searchPlaceResult.collectAsState() //주소검색결과
    var scrollToPosition by remember { mutableFloatStateOf(0F) }

    val checkRepImage = repBitmap != null
    val checkCategory = category.value.isNotEmpty()
    val checkTitle = title.value.length in (MIN_TITLE..MAX_TITLE)
    val checkLocation =
        (locationSelect.value == "주소 검색" && locationSearchValue.value.isNotEmpty() && placeSelected.value)
                || locationSelect.value == "위치정보 없음"
    val checkPerson =
        maxPerson.value.isNotEmpty() && maxPerson.value.toInt() in MIN_PERSON..MAX_PERSON
    val checkDate = startDate.value.isNotEmpty() && endDate.value.isNotEmpty() &&
            compareDates(startDate.value, endDate.value)

    val checkTime = startTime.value.isNotEmpty() && endTime.value.isNotEmpty()
            && compareTimes( // 시작 시간 < 종료 시간 -> true
        convertTimeFormat(startTime.value),
        convertTimeFormat(endTime.value)
    )
    val checkDetail = patDetail.value.length in (MIN_DETAIL..MAX_DETAIL)
    val checkDay = dayList.value.isNotEmpty()
    val checkProof = proofDetail.value.length in (MIN_PROOF..MAX_PROOF)
    val checkCorrect = correctBitmap != null && incorrectBitmap != null
    val checkList = listOf(
        checkRepImage, checkCategory, checkTime, checkLocation, checkPerson, checkDate,
        checkTime, checkDetail, checkDay, checkProof, checkCorrect
    )



    PostRepImageView(navController = navController, bitmap = repBitmap, viewModel = viewModel)
    Spacer(modifier = modifier.size(20.dp))

    Column(modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
        PostTitleBox(title = "카테고리 선택", approve = checkCategory) {
            Row {
                CategoryBoxList(state = category)
            }
        }

        PostTitleBox(title = "팟 제목", approve = checkTitle) {
            CustomTextField(
                placeholderText = "최대 15자",
                state = title, maxLength = 15
            )
        }

        PostTitleBox(
            modifier = modifier.onGloballyPositioned { coordinates ->
                scrollToPosition = coordinates.positionInRoot().y
            },
            title = "팟 상세정보", subTitle = "최대 5장 가능, 사진1부터 차례대로 표시돼요.",
            essential = false
        ) {
            SelectImageList(
                navController = navController,
                bitmapList = bodyBitmap, bitmapType = "BODY", viewModel = viewModel
            )
        }

        PostTitleBox(title = "위치정보", approve = checkLocation) {
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
                    scrollState = scrollState,
                    scrollToPosition = scrollToPosition,
                    placeSelected = placeSelected
                )
            }
        }

        PostTitleBox(title = "팟 인원 설정", approve = checkPerson) {
            CustomTextField(
                placeholderText = "숫자만 입력해주세요. (최대 10,000명 가능)",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                state = maxPerson, maxLength = 5,
            )
        }

        PostTitleBox(title = "시작일-종료일", approve = checkDate) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작일 선택", dateState = startDate,
                    formatter = convertDateFormat, widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { startDate.value = it },
                            onDismiss = { startPressed.value = !startPressed.value },
                        )
                    },
                    clickState = startPressed
                )
                Text(
                    "부터 시작",
                    modifier.padding(start = 8.dp, end = 10.dp),
                    style = Typography.bodySmall
                )
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료일 선택", dateState = endDate,
                    formatter = convertDateFormat, widthSize = 96.dp,
                    content = {
                        DateTimePickerView(
                            onDateSelected = { endDate.value = it },
                            onDismiss = { endPressed.value = !endPressed.value },
                        )
                    },
                    clickState = endPressed
                )
                Text("에 종료", modifier.padding(start = 8.dp), style = Typography.bodySmall)
            }
        }

        PostTitleBox(title = "인증 가능 시간", approve = checkTime) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val startPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "시작시간", dateState = startTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            startPressed.value = !startPressed.value
                        }, timeState = startTime)
                    },
                    clickState = startPressed
                )
                Text(
                    "부터",
                    modifier.padding(start = 8.dp, end = 10.dp),
                    style = Typography.bodySmall
                )
                val endPressed = remember { mutableStateOf(false) }
                CustomPicker(
                    text = "종료시간", dateState = endTime,
                    content = {
                        WheelTimePickerView(onDismiss = {
                            endPressed.value = !endPressed.value
                        }, timeState = endTime)
                    },
                    clickState = endPressed
                )
                Text("까지", modifier.padding(start = 8.dp), style = Typography.bodySmall)
            }
        }

        PostTitleBox(title = "팟 소개", approve = checkDetail) {
            CustomTextField(
                placeholderText = "최소 15자, 최대 500자",
                state = patDetail, maxLength = 500,
            )
        }

        PostTitleBox(title = "인증 빈도", approve = checkDay) {
            Row() {
                SelectDayButtonList(state = dayList)
            }
        }

        PostTitleBox(title = "인증방법 설명", approve = checkProof) {
            CustomTextField(
                placeholderText = "최소 5자, 최대 30자",
                maxLength = 30, state = proofDetail
            )
        }

        PostTitleBox(title = "인증방법 예시", approve = checkCorrect) {
            Row() {
                ExampleImageView(
                    navController = navController,
                    text = "올바른 예시", backColor = GreenBack, textColor = GreenText,
                    bitmap = correctBitmap, bitmapType = "CORRECT",
                    viewModel = viewModel
                )
                Spacer(modifier = modifier.size(10.dp))
                ExampleImageView(
                    navController = navController,
                    text = "잘못된 예시", backColor = RedBack, textColor = RedText,
                    bitmap = incorrectBitmap, bitmapType = "INCORRECT",
                    viewModel = viewModel
                )
            }
        }

        PostTitleBox(title = "인증 수단", subTitle = "실시간 촬영은 필수에요.", approve = true) {
            Row {
                CheckBoxView(text = "실시간 촬영")
                Spacer(modifier = modifier.size(12.dp))
                CheckBoxView(checked = isRealTime, text = "갤러리에서 사진 가져오기", isRealTime = true)
            }
        }

        if (checkList.all { it }) {
            FinalButton(text = "확정",
                backColor = PrimaryMain,
                textColor = White,
                onClick = {
                    val outputStartTime = convertTimeFormat(startTime.value)
                    val outputEndTime = convertTimeFormat(endTime.value)

                    viewModel.post(
                        patName = title.value,
                        maxPerson = maxPerson.value.toInt(),
                        patDetail = patDetail.value,
                        proofDetail = proofDetail.value,
                        startDate = startDate.value,
                        endDate = endDate.value,
                        startTime = outputStartTime,
                        endTime = outputEndTime,
                        days = dayList.value,
                        category = category.value,
                        realtime = !isRealTime.value,
                    )
                })
        } else {
            FinalButton(
                text = "필수 항목을 모두 채워주세요",
                backColor = Gray300, textColor = White, stokeColor = Gray300
            )
        }
    }
}


@Composable
fun SelectLocationButtonList(
    modifier: Modifier = Modifier,
    locationState: MutableState<String>,
    postViewModel: PostViewModel,
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
            locationButtonView(
                modifier = modifier.weight(1f), location
            )
        }
    }
    Spacer(modifier.padding(top = 16.dp))

    when (locationState.value) {
        "주소 검색" -> {
            SearchPlaceTextField(
                placeholderText = "서초동 스타벅스",
                maxLength = 30,
                state = searchValue,
                onScreen = onSearchScreen,
                viewModel = postViewModel,
                maxLines = 1,
                placeSelected = placeSelected,
                inputEnter = {
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
                    postViewModel = postViewModel,
                    placeSelected = placeSelected
                )
            }
        }

        "위치정보 없음" -> Box(contentAlignment = Alignment.Center) {
            postViewModel.selectPlace()
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
