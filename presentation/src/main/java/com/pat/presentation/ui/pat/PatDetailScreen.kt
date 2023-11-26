package com.pat.presentation.ui.pat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orhanobut.logger.Logger
import com.pat.domain.model.pat.PatDetailContent
import com.pat.presentation.R
import com.pat.presentation.ui.common.CategoryBox
import com.pat.presentation.ui.common.IconWithTextView
import com.pat.presentation.ui.common.ExampleImageView
import com.pat.presentation.ui.common.FinalButton
import com.pat.presentation.ui.common.SimpleTextView
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray50
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.GreenBack
import com.pat.presentation.ui.theme.GreenText
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.RedBack
import com.pat.presentation.ui.theme.RedText
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatDetailView(
    navController: NavController,
    patDetailViewModel: PatDetailViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by patDetailViewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "강아지 산책",
                        fontSize = 14.sp, //TODO STYLE
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_back_arrow),
                            contentDescription = "Go back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_write),
                            contentDescription = "Write"
                        )
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
            PostDetailScreen(
                navController = navController,
                content = uiState.content,
                patDetailViewModel = patDetailViewModel
            )
        }
    }
}

@Composable
fun PostDetailScreen(
    navController: NavController,
    content: PatDetailContent?,
    patDetailViewModel: PatDetailViewModel,
    modifier: Modifier = Modifier
) {
    var isOpenBtnClicked by remember { mutableStateOf(false) }
    Logger.t("patdetail").i("${content}")
    if (content != null) {
        GlideImage(
            modifier = modifier
                .fillMaxWidth()
                .height(160.dp),
            imageModel = { content.repImg })
        Column(modifier = modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryBox(
                    modifier = modifier.padding(8.dp),
                    category = content.category,
                    isSelected = true
                )
                Text(
                    style = Typography.displayLarge,
                    text = "강아지 산책",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = modifier.padding(3.dp)
                )
            }
            Spacer(modifier.height(16.dp))
            PatSimpleInfo(
                location = content.location,
                startDate = content.startDate,
                endDate = content.endDate,
                nowPerson = content.nowPerson,
                maxPerson = content.maxPerson
            )
            Spacer(modifier.size(20.dp))
            UserInfo()
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Gray50)
            )
            Spacer(modifier.size(20.dp))

            Text("팟 상세정보", fontSize = 16.sp, modifier = modifier.padding(3.dp))
            GlideImage(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp),
                imageModel = { content.bodyImg.first() }
            )
            Spacer(modifier.size(10.dp))
            Column {
                if (isOpenBtnClicked) {
                    PatPhotos(patUriInfo = content.bodyImg)
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
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Gray50)
            )

            Text(
                content.patDetail, fontSize = 16.sp, modifier = modifier
                    .padding(3.dp)
                    .fillMaxWidth()
            )
            IconWithTextView("강아지와 주 2회 산책해요", iconResource = R.drawable.ic_chat_dot)

            Spacer(modifier.size(20.dp))

            Text("인증 가능 시간", fontSize = 16.sp, modifier = modifier.padding(3.dp))
            IconWithTextView("오전 11시부터 오후 11시까지", iconResource = R.drawable.ic_alram)

            Spacer(modifier.size(20.dp))
            Text("인증 빈도", fontSize = 16.sp, modifier = modifier.padding(3.dp))
            CategoryButtonList()

            Spacer(modifier.size(20.dp))

            Text("시작일 - 종료일", fontSize = 16.sp, modifier = modifier.padding(3.dp))
            DateText(
                startDate = content.startDate,
                endDate = content.endDate,
                iconResource = R.drawable.ic_calendar
            )
            Spacer(modifier.size(20.dp))

            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(Gray50)
            )

            Spacer(modifier.size(20.dp))
            Text("인증 방법", fontSize = 16.sp, modifier = modifier.padding(3.dp))
            IconWithTextView(
                "목줄을 찬 반려동물이 바깥 풍경과 함꼐 나오도록 사진을 찍어주세요.",
                iconResource = R.drawable.ic_chat_check
            )
            Spacer(modifier.size(20.dp))
//            Row() {
//                ExampleImageView(
//                    navController = navController,
//                    text = "올바른 예시",
//                    backColor = GreenBack,
//                    textColor = GreenText,
//                    bitmap =
//                )
//                Spacer(modifier = modifier.size(10.dp))
//                ExampleImageView(
//                    navController = navController,
//                    text = "잘못된 예시",
//                    backColor = RedBack,
//                    textColor = RedText
//                )
//            }

            Spacer(modifier.size(20.dp))
            Text("인증 수단", fontSize = 16.sp, modifier = modifier.padding(3.dp))
            if (content.realtime) {
                IconWithTextView("실시간 촬영", iconResource = R.drawable.ic_camera)
            }
            Spacer(modifier.height(7.dp))
            IconWithTextView("갤러리에서 사진 가져오기", iconResource = R.drawable.ic_gallery)

            Spacer(modifier.size(44.dp))
            FinalButton(text = "팟 참여하기",
                backColor = PrimaryMain,
                textColor = White,
                onClick = { patDetailViewModel.participatePat() }
            )
        }
    }
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    userName: String = "유저 닉네임"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        //유저이미지정보
        Text(
            userName,
            style = Typography.labelLarge,
            color = Gray700,
            fontSize = 15.sp,
            modifier = modifier.padding(end = 6.dp)
        )
        Text("개설자", style = Typography.displaySmall, color = Gray500, fontSize = 12.sp)
    }

}

@Composable
fun PatSimpleInfo(
    modifier: Modifier = Modifier,
    location: String = "서울시 관악구 신사동",
    startDate: String = "12.5(금)",
    endDate: String = "12.25(금)",
    nowPerson: Int = 8,
    maxPerson: Int = 10,
    textColor: Color = Color(0xFF009D65),
) {
    Column() {
        SimpleTextView(text = location, vectorResource = R.drawable.ic_map, spacePadding = 6.dp)
        Spacer(modifier.height(8.dp))
        SimpleTextView(
            text = "$startDate - $endDate",
            vectorResource = R.drawable.ic_calendar,
            spacePadding = 6.dp
        )
        Spacer(modifier.height(8.dp))
        SimpleTextView(
            text = "현재 $nowPerson / $maxPerson",
            vectorResource = R.drawable.ic_user,
            spacePadding = 6.dp
        )
    }
}

@Composable
fun DateText(
    startDate: String? = "11월 13일(월)",
    endDate: String? = "11월 27일(월)",
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
                Text(text = "${startDate}")
                Spacer(modifier = modifier.width(12.dp))
                Text(text = "종료", color = PrimaryMain)
                Text(text = "${endDate}")
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


@Composable
fun DayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    buttonColor: Color = Primary50,
    textColor: Color = PrimaryMain,
    border: Color = PrimaryMain
) {

    Button(
        modifier = modifier
            .requiredHeight(32.dp),
        shape = RoundedCornerShape(22.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        enabled = enabled,
        border = BorderStroke(1.dp, border),
        onClick = {
            onClick()
        },
    ) {
        Text(
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium,
            text = text,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = textColor
        )
    }
}

@Composable
fun CategoryButtonList() {
    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
    val proofDays = listOf("월요일")
    Column {
        Row(
        ) {
            days.take(5).forEach { day ->
                if (day in proofDays) {
                    DayButton(text = day)
                } else {
                    DayButton(
                        text = day,
                        buttonColor = White,
                        textColor = Gray500,
                        border = Gray500
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
        ) {
            days.takeLast(2).forEach { day ->
                if (day in proofDays) {
                    DayButton(text = day)

                } else {
                    DayButton(
                        text = day,
                        buttonColor = White,
                        textColor = Gray500,
                        border = Gray500
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
    Spacer(Modifier.size(10.dp))

}
