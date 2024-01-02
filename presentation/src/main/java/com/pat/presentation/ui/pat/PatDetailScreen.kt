package com.pat.presentation.ui.pat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pat.domain.model.pat.PatDetailContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.DayButtonList
import com.pat.presentation.ui.common.Divider
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.IconWithTextView
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.common.SnackBar
import com.pat.presentation.ui.common.convertDateViewFormat
import com.pat.presentation.ui.common.convertTimeViewFormat
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray300
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Gray800
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatDetailView(
    modifier: Modifier = Modifier,
    patDetailViewModel: PatDetailViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by patDetailViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(patDetailViewModel) {
        patDetailViewModel.event.collect {
            when (it) {
                is ParticipateEvent.ParticipateSuccess -> {
                    errorMessage.value = "참여 성공"
                }

                is ParticipateEvent.ParticipateFailed -> {
                    errorMessage.value = "참여 실패"
                }

                is ParticipateEvent.GetPatDetailSuccess -> {
                }

                is ParticipateEvent.GetPatDetailFailed -> {
                    errorMessage.value = "데이터를 불러오지 못했어요"
                }

                is ParticipateEvent.WithdrawSuccess -> {
                    errorMessage.value = "취소 성공."
                }

                is ParticipateEvent.WithdrawFailed -> {
                    errorMessage.value = "취소 실패"
                }
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = uiState.content?.patName ?: "공고글 상세",
                        style = Typography.labelMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
                            contentDescription = "Go back",
                            tint = Gray800
                        )
                    }
                },
                actions = {
                    if (uiState.content?.isWriter == true) {
                        IconButton(modifier = modifier
                            .padding(end = 12.dp)
                            .size(24.dp), onClick = {
                            navController.navigate("patUpdate/${uiState.content?.patId}")
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_write),
                                contentDescription = "Write",
                                tint = Gray800
                            )
                        }
                    }
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
        ) {
            if (uiState.content != null) {
                PatDetailScreen(
                    navController = navController,
                    content = uiState.content!!,
                    patDetailViewModel = patDetailViewModel
                )
            }
        }

        if (errorMessage.value.isNotEmpty()) {
            SnackBar(errorMessage)
        }
    }
}

@Composable
fun PatDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    content: PatDetailContent,
    patDetailViewModel: PatDetailViewModel,
) {
    GlideImage(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        imageModel = { content.repImg })
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryBox(
                modifier = modifier.padding(end = 8.dp),
                category = content.category,
                isSelected = true
            )
            Text(
                style = Typography.displayLarge,
                text = content.patName,
                fontSize = 20.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier.height(16.dp))
        PatSimpleInfo(
            location = content.location.ifEmpty { "어디서나 가능" },
            startDate = convertDateViewFormat(content.startDate),
            endDate = convertDateViewFormat(content.endDate),
            nowPerson = content.nowPerson,
            maxPerson = content.maxPerson
        )
        Spacer(modifier.size(20.dp))
        UserInfo(
            userName = content.nickname,
            userProfile = content.profileImg,
            isOpener = content.isWriter
        )
        Spacer(modifier.size(8.dp))
        if (content.bodyImg.isNotEmpty()) {
            Divider()
            Spacer(modifier.size(24.dp))
            Text("팟 상세정보", style = Typography.titleLarge, color = Gray800)
            Spacer(modifier.size(16.dp))
            GlideImage(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp),
                imageModel = { content.bodyImg.first() }
            )
            if (content.bodyImg.size >= 2) { // 사진이 2장 이상인 경우
                var isOpenBtnClicked by remember { mutableStateOf(false) }
                Spacer(modifier.size(10.dp))
                Column {
                    if (isOpenBtnClicked) {
                        PatPhotos(patUriInfo = content.bodyImg)
                        Spacer(modifier = modifier.size(10.dp))
                        FinalButton(
                            text = "접기",
                            backColor = White,
                            textColor = PrimaryMain,
                            stokeColor = PrimaryMain,
                            stokeWidth = 1.dp,
                            onClick = {
                                isOpenBtnClicked = false
                            }
                        )
                    } else {
                        FinalButton(
                            text = "펼쳐보기",
                            backColor = White,
                            textColor = PrimaryMain,
                            stokeColor = PrimaryMain,
                            stokeWidth = 1.dp,
                            onClick = {
                                isOpenBtnClicked = true
                            }
                        )
                    }
                }
                Spacer(modifier.size(20.dp))
            }
        }
        Divider()
        Spacer(modifier.size(24.dp))
        Text("팟 소개", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        IconWithTextView(content.patDetail, iconResource = R.drawable.ic_chat_dot)
        Spacer(modifier.size(28.dp))

        Text("위치 정보", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        IconWithTextView(content.location.ifEmpty { "어디서나 가능" }, iconResource = R.drawable.ic_map)
        Spacer(modifier.size(28.dp))

        Text("인증 가능 시간", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        IconWithTextView(
            "${convertTimeViewFormat(content.startTime)}부터 ${convertTimeViewFormat(content.endTime)}까지",
            iconResource = R.drawable.ic_alram
        )
        Spacer(modifier.size(28.dp))

        Text("인증 빈도", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        DayButtonList(content.dayList)
        Spacer(modifier.size(28.dp))

        Text("시작일 - 종료일", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        DateText(
            startDate = convertDateViewFormat(content.startDate),
            endDate = convertDateViewFormat(content.endDate),
            iconResource = R.drawable.ic_calendar
        )
        Spacer(modifier.size(28.dp))

        Text("인증 방법", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        IconWithTextView(
            content.proofDetail,
            iconResource = R.drawable.ic_chat_check
        )
        Spacer(modifier.size(28.dp))
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Column {
                GlideImage(
                    modifier = modifier.size(130.dp),
                    imageModel = { content.correctImg })
                Box(
                    modifier
                        .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                        .background(GreenBack)
                        .height(26.dp)
                        .width(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "올바른 예시",
                        style = Typography.labelMedium,
                        fontSize = 12.sp,
                        color = GreenText
                    )
                }
            }
            Spacer(modifier = modifier.size(10.dp))
            Column {
                GlideImage(
                    modifier = modifier.size(130.dp),
                    imageModel = { content.incorrectImg })
                Box(
                    modifier
                        .clip(RoundedCornerShape(bottomEnd = 4.dp, bottomStart = 4.dp))
                        .background(RedBack)
                        .height(26.dp)
                        .width(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "잘못된 예시",
                        style = Typography.labelMedium,
                        fontSize = 12.sp,
                        color = RedText
                    )
                }
            }
        }
        Spacer(modifier.size(28.dp))
        Text("인증 수단", style = Typography.titleLarge, color = Gray800)
        Spacer(modifier.size(14.dp))
        if (content.realtime) {
            IconWithTextView("실시간 촬영", iconResource = R.drawable.ic_camera)
            Spacer(modifier.size(44.dp))
        } else {
            IconWithTextView("실시간 촬영", iconResource = R.drawable.ic_camera)
            Spacer(modifier.height(7.dp))
            IconWithTextView("갤러리에서 사진 가져오기", iconResource = R.drawable.ic_gallery)
            Spacer(modifier.size(44.dp))

        }



        if (content.isJoiner) {
            if (!content.isWriter) { // 작성자가 아니면서 참여자일 때, 작성자면 아무것도 안보임.
                when (content.state) {
                    "CANCELABLE" -> {
                        FinalButton(
                            text = "팟 취소하기 (시작 하루 전까지 취소 가능)",
                            backColor = PrimaryMain,
                            textColor = White,
                            onClick = {
                                patDetailViewModel.withdrawPat(content.patId)
                            }
                        )
                    }

                    "NO_CANCELABLE" -> {
                        FinalButton(
                            text = "취소가 불가능해요! (시작 하루 전까지 취소 가능)",
                            backColor = Gray300,
                            textColor = White,
                            stokeColor = Gray300,
                        )
                    }

                    "IN_PROGRESS" -> {
                        FinalButton(
                            text = "인증이 이미 진행중인 팟이에요!",
                            backColor = Gray300,
                            textColor = White,
                            stokeColor = Gray300,
                        )
                    }

                    "COMPLETED" -> {
                        FinalButton(
                            text = "종료된 팟이에요!",
                            backColor = Gray300,
                            textColor = White,
                            stokeColor = Gray300,
                        )
                    }
                }
            }
        } else { // 참여자가 아니면
            if (content.state == "COMPLETED") {
                FinalButton(
                    text = "종료된 팟이에요!",
                    backColor = Gray300,
                    textColor = White,
                )
            } else { // 종료 상태가 아니면
                FinalButton(text = "팟 참여하기",
                    backColor = PrimaryMain,
                    textColor = White,
                    // 비회원 / 회원 상태에 따라 달라져야 함
                    onClick = { patDetailViewModel.participatePat() }
                )
            }
        }
    }
}


@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    userName: String,
    userProfile: String,
    isOpener: Boolean
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            modifier = Modifier
                .padding(end = 8.dp)
                .requiredSize(40.dp)
                .clip(CircleShape),
            imageModel = { userProfile })
        Text(
            userName,
            style = Typography.labelLarge,
            color = Gray700,
            fontSize = 15.sp,
            modifier = modifier.padding(end = 6.dp)
        )
        if (isOpener) Text(
            "개설자",
            style = Typography.displaySmall,
            color = Gray500,
            fontSize = 12.sp
        )
    }

}

@Composable
fun PatSimpleInfo(
    modifier: Modifier = Modifier,
    location: String,
    startDate: String,
    endDate: String,
    nowPerson: Int,
    maxPerson: Int,
    textColor: Color = Color(0xFF009D65),
) {
    Column() {
        SimpleTextView(
            text = location.ifEmpty { "어디서나 가능" },
            vectorResource = R.drawable.ic_map,
            spacePadding = 6.dp,
            iconSize = 16.dp,
            style = Typography.bodySmall
        )
        Spacer(modifier.height(8.dp))
        SimpleTextView(
            text = "$startDate - $endDate",
            vectorResource = R.drawable.ic_calendar,
            spacePadding = 6.dp,
            iconSize = 16.dp,
            style = Typography.bodySmall
        )
        Spacer(modifier.height(8.dp))
        SimpleTextView(
            text = "현재 ${nowPerson}명 / ${maxPerson}명",
            vectorResource = R.drawable.ic_user,
            spacePadding = 6.dp,
            iconSize = 16.dp,
            style = Typography.bodySmall
        )
    }
}

@Composable
fun DateText(
    startDate: String?,
    endDate: String?,
    iconResource: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .border(
                BorderStroke(1.dp, Gray200),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconResource),
                contentDescription = null,
                tint = PrimaryMain
            )

            Spacer(modifier = modifier.width(8.dp))
            Row() {
                Text(text = "시작", color = PrimaryMain)
                Text(text = "$startDate")
                Spacer(modifier = modifier.width(12.dp))
                Text(text = "종료", color = PrimaryMain)
                Text(text = "$endDate")
            }
        }
    }
}


@Composable
fun PatPhotos(modifier: Modifier = Modifier, patUriInfo: List<String>) {
    Column() {
        patUriInfo.forEachIndexed { index, uri ->
            if (index != 0) {
                GlideImage(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    imageModel = { uri })
            }
        }
    }
}

