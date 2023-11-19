package com.pat.presentation.ui.pat

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pat.presentation.R
import com.pat.presentation.ui.home.components.CategoryButton
import com.pat.presentation.ui.theme.Gray200
import com.pat.presentation.ui.theme.Gray400
import com.pat.presentation.ui.theme.Gray50
import com.pat.presentation.ui.theme.Gray500
import com.pat.presentation.ui.theme.Gray700
import com.pat.presentation.ui.theme.Primary50
import com.pat.presentation.ui.theme.PrimaryMain
import com.pat.presentation.ui.theme.Typography
import com.pat.presentation.ui.theme.White
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailView(modifier: Modifier = Modifier) {
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
                            imageVector = Icons.Default.ArrowBackIosNew,
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
            PostDetailScreen()
        }
    }
}

@Composable
fun PostDetailScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null
    )
    Column(modifier = modifier.padding(10.dp)) {
        Row() {
            Box(
                modifier = modifier
                    .width(40.dp)
                    .height(35.dp)
                    .background(
                        color = Color.Red,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Text(
                    text = "일상",
                    modifier = modifier
                        .padding(8.dp)
                        .align(Alignment.Center),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
            Text(
                text = "강아지 산책",
                fontSize = 20.sp,
                modifier = modifier.padding(3.dp)
            )
        }
        Spacer(modifier.height(16.dp))
        PatSimpleInfo()
        Spacer(modifier.size(20.dp))
        UserInfo()
        Box(modifier = modifier.fillMaxWidth().height(10.dp).background(Gray50))
        Spacer(modifier.size(20.dp))

        //TODO 펼쳐보기 만들어야 함
        Text("팟 상세정보", fontSize = 16.sp, modifier = modifier.padding(3.dp))
//        PatPhotos()
        Spacer(modifier.size(20.dp))
        Box(modifier = modifier.fillMaxWidth().height(10.dp).background(Gray50))

        Text(
            "팟 소개", fontSize = 16.sp, modifier = modifier
                .padding(3.dp)
                .fillMaxWidth()
        )
        CustomText("강아지와 주 2회 산책해요", painterResource(id = R.drawable.ic_chat_dot))

        Spacer(modifier.size(20.dp))

        Text("인증 가능 시간", fontSize = 16.sp, modifier = modifier.padding(3.dp))
        CustomText("오전 11시부터 오후 11시까지", painterResource(id = R.drawable.ic_alram))

        Spacer(modifier.size(20.dp))
        Text("인증 빈도", fontSize = 16.sp, modifier = modifier.padding(3.dp))
        CategoryButtonList()

        Spacer(modifier.size(20.dp))

        Text("시작일 - 종료일", fontSize = 16.sp, modifier = modifier.padding(3.dp))
        DateText(painter = painterResource(id = R.drawable.ic_calendar))
        Spacer(modifier.size(20.dp))

        Box(modifier = modifier.fillMaxWidth().height(10.dp).background(Gray50))

        Spacer(modifier.size(20.dp))
        Text("인증 방법", fontSize = 16.sp, modifier = modifier.padding(3.dp))
        CustomText(
            "목줄을 찬 반려동물이 바깥 풍경과 함꼐 나오도록 사진을 찍어주세요.",
            painterResource(id = R.drawable.ic_chat_check)
        )


        Spacer(modifier.size(20.dp))
        Text("인증 수단", fontSize = 16.sp, modifier = modifier.padding(3.dp))
        CustomText("실시간 촬영", painterResource(id = R.drawable.ic_camera))
        Spacer(modifier.height(7.dp))

        CustomText("갤러리에서 사진 가져오기", painterResource(id = R.drawable.ic_gallery))

        Spacer(modifier.size(44.dp))
        ParticipatePatButton()
    }
}

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    userName : String ="유저 닉네임"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        //유저이미지정보
        Text(userName, style = Typography.labelLarge, color = Gray700, fontSize = 15.sp, modifier = modifier.padding(end = 6.dp))
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(tint= Gray400 ,painter = painterResource(id = R.drawable.ic_map), contentDescription = null)
            Spacer(modifier.width(6.dp))
            Text(text = location)
        }
        Spacer(modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(tint= Gray400 ,
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = null
            )
            Spacer(modifier.width(6.dp))
            Text(text = "$startDate - $endDate")
        }
        Spacer(modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(tint= Gray400 ,painter = painterResource(id = R.drawable.ic_user), contentDescription = null)
            Spacer(modifier.width(6.dp))
            Text(text = "현재 $nowPerson / $maxPerson")
        }
    }

}

@Composable
fun DateText(
    startDate: String? = "11월 13일(월)",
    endDate: String? = "11월 27일(월)",
    painter: Painter,
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
                painter = painter, contentDescription = null, tint = PrimaryMain
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
fun CustomText(
    title: String,
    painter: Painter,
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
                painter = painter, contentDescription = null, tint = PrimaryMain
            )

            Spacer(modifier = modifier.width(8.dp))
            Text(text = title, modifier = modifier.padding(3.dp))
        }
    }
}

@Composable
fun ParticipatePatButton(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(46.dp)
            .clickable(onClick = {})
            .clip(RoundedCornerShape(4.dp))
            .background(color = PrimaryMain), contentAlignment = Alignment.Center
    ) {
        Text("팟 참여하기", color = Color.White, fontSize = 16.sp)
    }
}


@Composable
fun PatPhotos(modifier: Modifier = Modifier) {
    Row() {
        LazyRow() {
            items(5) {
                PreviewPhotos()
            }
        }
    }
}

@Composable
fun PreviewPhotos(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.ic_add),
        contentDescription = null
    )
}

@Composable
fun DayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String,
    enabled: Boolean = true,
    buttonColor : Color = Primary50,
    textColor :Color = PrimaryMain,
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
fun CategoryButtonList(stateList: String? = null) {
    // stateList는 서버에서 가져온다
    val days = listOf<String>("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일")
    val proofDays = listOf<String>("월요일", "목요일", "토요일")

    Column {
        // First row (월요일 to 금요일)
        Row(
        ) {
            days.take(5).forEach { day ->
                if (day in proofDays) {
                    DayButton(text = day)
                } else {
                    DayButton(text = day, buttonColor = White, textColor = Gray500,border=Gray500)
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
                   DayButton(text = day, buttonColor = White, textColor = Gray500,border=Gray500)
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
    Spacer(Modifier.size(10.dp))

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PostDetailView()
}