package com.pat.presentation.ui.proof

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orhanobut.logger.Logger
import com.pat.domain.model.proof.ProofContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.ExampleImageView
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.IconWithTextView
import com.pat.presentation.ui.common.SelectImage
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.pat.CategoryButtonList
import com.pat.presentation.ui.pat.DateText
import com.pat.presentation.ui.theme.FailCircleColor
import com.pat.presentation.ui.theme.FailTextColor
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray50
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray600
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.Gray900
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.RemainColor
import com.pat.presentation.ui.theme.SuccessCircleColor
import com.pat.presentation.ui.theme.SuccessTextColor
import com.pat.presentation.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PattingScreenView(
    modifier: Modifier = Modifier,
    pattingViewModel: ProofViewModel = hiltViewModel()
) {
    val uiState by pattingViewModel.uiState.collectAsState()
    LaunchedEffect(uiState.content) {
        Logger.t("MainTest").i("${uiState.content}")
    }
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "팟 상세 페이지",
                        style = Typography.labelMedium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
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
        ) {
            PattingScreen(content = uiState.content)
        }
    }
}

@Composable
fun PattingScreen(
    modifier: Modifier = Modifier,
    content: List<ProofContent>?
) {
    var spreadState by remember { mutableStateOf(false) }
    var myProofState by remember { mutableStateOf(true) }


    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CategoryBox(
                category = "환경",
                isSelected = true
            )
            Spacer(modifier = modifier.padding(horizontal = 8.dp))
            Text(
                style = Typography.displayLarge,
                text = "강아지 산책",
                fontSize = 20.sp,
                color = Gray900,
                modifier = modifier.padding(3.dp)
            )
            Spacer(modifier.weight(1f))
            Row(modifier.clickable {
                // TODO 모집 공고로 이동 처리
            }) {
                Text(
                    style = Typography.displaySmall,
                    text = "모집 공고로 이동",
                    color = Gray600,
                    modifier = modifier.padding(3.dp)
                )
                Icon(
                    modifier = modifier.size(24.dp),
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_caret_right
                    ),
                    contentDescription = null,
                    tint = Gray600
                )
            }
        }
        Spacer(modifier.height(16.dp))
        Column(
            modifier
                .fillMaxWidth()
                .height(75.dp)
                .border(1.dp, Gray300, RoundedCornerShape(6.dp))
                .padding(horizontal = 12.dp, vertical = 14.dp)
        ) {
            Row() {
                SimpleTextView(
                    text = "서울시 관악구 신사동",
                    vectorResource = R.drawable.ic_map,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
                Spacer(modifier = modifier.padding(horizontal = 8.dp))
                SimpleTextView(
                    text = "11월 13일 - 11월 27일",
                    vectorResource = R.drawable.ic_calendar,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
            }
            Spacer(modifier = modifier.padding(bottom = 12.dp))
            Row() {
                SimpleTextView(
                    text = "오전 11시 - 오후 11시",
                    vectorResource = R.drawable.ic_alram,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
                Spacer(modifier = modifier.padding(horizontal = 8.dp))
                SimpleTextView(
                    text = "월,수,금 인증",
                    vectorResource = R.drawable.na_circle_check,
                    spacePadding = 6.dp,
                    style = Typography.labelSmall.copy(
                        fontSize = 14.sp,
                        color = Gray800
                    )
                )
            }
        }
        Spacer(modifier.padding(bottom = 20.dp))
        Row(modifier.clickable {
            spreadState = !spreadState
        }, verticalAlignment = Alignment.CenterVertically) {
            Text("팟 상세정보 펼치기", style = Typography.titleLarge, color = Gray800)
            Spacer(modifier.weight(1f))
            Icon(
                modifier = modifier.size(42.dp),
                imageVector = ImageVector.vectorResource(
                    id = if (!spreadState) R.drawable.ic_caret_down else R.drawable.ic_caret_up_sm
                ),
                contentDescription = null,
                tint = Gray800
            )
        }

        Spacer(modifier = modifier.padding(top = 18.5.dp))
        Box(
            modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Gray50)
        )
        if (spreadState) {
            Column(modifier.padding(top = 24.dp)) {
                Text("팟 소개", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                IconWithTextView(
                    "반려동물과 함께 하루 2번 산책을 함께해요!",
                    iconResource = R.drawable.ic_chat_dot
                )
                Spacer(modifier.padding(bottom = 28.dp))

                Text("위치 정보", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                IconWithTextView(
                    "서울특별시 강남구 테헤란로 123",
                    iconResource = R.drawable.ic_map
                )
                Spacer(modifier.padding(bottom = 28.dp))

                Text("시작일 - 종료일", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                DateText(
                    iconResource = R.drawable.ic_calendar
                )
                Spacer(modifier.padding(bottom = 28.dp))

                Text("인증 빈도", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                CategoryButtonList()
                Spacer(modifier.padding(bottom = 28.dp))

                Text("인증 가능시간", style = Typography.titleLarge, color = Gray800)
                Spacer(modifier.padding(bottom = 14.dp))
                IconWithTextView(
                    "오전 11시부터 오후 11시까지",
                    iconResource = R.drawable.ic_alram
                )
                Spacer(modifier.padding(bottom = 28.dp))
            }
        }

        Spacer(modifier.padding(bottom = 34.dp))
        Text("인증 방법", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.padding(bottom = 14.dp))
        IconWithTextView(
            "목줄을 찬 반려동물이 바깥 풍경과 함꼐 나오도록 사진을 찍어주세요.",
            iconResource = R.drawable.ic_chat_check
        )
        Spacer(modifier.padding(bottom = 14.dp))
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            ExampleImageView(
                text = "올바른 예시",
                backColor = GreenBack,
                textColor = GreenText,
                hasSource = "여기에 사진 uri 추가"
            )
            Spacer(modifier = modifier.size(10.dp))
            ExampleImageView(
                text = "잘못된 예시",
                backColor = RedBack,
                textColor = RedText,
                hasSource = "여기에 uri 추가"
            )
        }
        Spacer(modifier = modifier.padding(top = 28.dp))
        Box(
            modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Gray50)
        )
        Row(modifier = modifier.padding(top = 24.dp)) {
            val underLine = modifier.drawBehind {
                val strokeWidthPx = 1.dp.toPx()
                val verticalOffset = size.height - 2.sp.toPx()
                drawLine(
                    color = PrimaryMain,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, verticalOffset),
                    end = Offset(size.width, verticalOffset)
                )
            }
            Box(modifier.clickable {
                myProofState = true
            }) {
                Text(
                    modifier = if (myProofState) underLine else modifier,
                    text = "내 인증 현황",
                    style = Typography.titleLarge,
                    color = if (myProofState) Gray800 else Gray500
                )

            }
            Spacer(modifier = modifier.padding(end = 14.dp))
            Box(modifier.clickable {
                myProofState = false
            }) {
                Text(
                    modifier = if (!myProofState) underLine else modifier,
                    text = "다른 참여자 인증",
                    style = Typography.titleLarge,
                    color = if (!myProofState) Gray800 else Gray500
                )
            }
        }
        Spacer(modifier = modifier.padding(bottom = 24.dp))
        if (myProofState) {
            ProofStatus(success = 24, fail = 8)
        } else {
            ProofStatus(success = 24, fail = 8, isAll = "전체 ")
        }
        FinalButton(text = "인증하기")
    }
}

@Composable
fun ProofStatus(
    modifier: Modifier = Modifier,
    success: Int,
    fail: Int,
    isAll: String = "",
    imgUriList: List<String> = listOf()
) {
    var title = if (isAll == "") "나의 인증사진" else "참여자들의 인증사진"
    Row() {
        Text("${isAll}인증 성공 횟수", style = Typography.titleLarge, color = Gray600, fontSize = 14.sp)
        Spacer(modifier = modifier.weight(1f))
        Text("3회 성공", style = Typography.titleLarge, color = PrimaryMain, fontSize = 18.sp)
        Spacer(modifier = modifier.padding(end = 5.dp))
        Text("/ 총 20회", style = Typography.labelSmall, color = Gray600, fontSize = 14.sp)
    }
    Row(modifier = modifier.padding(top = 12.dp)) {
        Text("${isAll}인증 실패 횟수", style = Typography.titleLarge, color = Gray600, fontSize = 14.sp)
        Spacer(modifier = modifier.weight(1f))
        Text("0회 실패", style = Typography.titleLarge, color = Gray600, fontSize = 18.sp)
        Spacer(modifier = modifier.padding(end = 5.dp))
        Text("/ 총 20회", style = Typography.labelSmall, color = Gray600, fontSize = 14.sp)
    }
    Spacer(modifier = modifier.padding(bottom = 28.dp))
    Text(title, style = Typography.titleLarge, color = Gray800)

    // TODO 연동 시 lazyRow로 수정
    Row(modifier.padding(top = 12.dp)) {
        SelectImage()
        Spacer(modifier = modifier.padding(end = 10.dp))
        SelectImage()
    }
    Row(modifier.padding(top = 24.dp)) {
        RatioText(
            text = "성공률",
            textColor = SuccessTextColor,
            circleColor = SuccessCircleColor,
            percentage = success
        )
        Spacer(modifier = modifier.padding(end = 12.dp))
        RatioText(
            text = "실패율",
            textColor = FailTextColor,
            circleColor = FailCircleColor,
            percentage = fail
        )
        Spacer(modifier = modifier.padding(end = 12.dp))
        RatioText(
            text = "남은 달성률",
            textColor = Gray700,
            circleColor = RemainColor,
            percentage = 100 - success - fail
        )
    }
    PercentBar(success = success, fail = fail)
    Spacer(modifier = modifier.padding(bottom = 36.dp))
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PercentBar(modifier: Modifier = Modifier, success: Int, fail: Int) {
    FlowRow(
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier
                .fillMaxWidth(success / 100f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 100.dp, bottomStart = 100.dp))
                .background(SuccessCircleColor)
        )
        Box(
            modifier
                .fillMaxWidth(fail / 100f)
                .fillMaxHeight()
                .background(FailCircleColor)
        )
        Box(
            modifier
                .fillMaxWidth((100 - success - fail) / 100f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp))
                .background(Gray200)
        )
    }
}

@Composable
fun RatioText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    circleColor: Color,
    percentage: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(circleColor)
        )
        Spacer(modifier = modifier.padding(end = 6.dp))
        Text(text = text, style = Typography.labelSmall, fontSize = 14.sp, color = Gray700)
        Spacer(modifier = modifier.padding(end = 6.dp))
        Text(
            text = "$percentage%",
            style = Typography.labelSmall,
            fontSize = 14.sp,
            color = textColor
        )
    }
}